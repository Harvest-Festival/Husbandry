package uk.joshiejack.husbandry.entity.stats;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.*;
import uk.joshiejack.husbandry.entity.ai.AbstractMoveToBlockGoal;
import uk.joshiejack.husbandry.entity.traits.TraitType;
import uk.joshiejack.husbandry.entity.traits.product.AbstractMobProductTrait;
import uk.joshiejack.husbandry.entity.traits.product.MoreProductChanceTrait;
import uk.joshiejack.husbandry.network.SendDataPacket;
import uk.joshiejack.husbandry.network.SetHappinessPacket;
import uk.joshiejack.husbandry.network.SpawnHeartsPacket;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.util.helpers.generic.MathsHelper;
import uk.joshiejack.penguinlib.util.helpers.generic.ReflectionHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

import static uk.joshiejack.husbandry.entity.stats.CapabilityStatsHandler.MOB_STATS_CAPABILITY;

public class MobStats<E extends MobEntity> implements ICapabilityProvider, INBTSerializable<CompoundNBT>, IMobStats<E> {
    private static final Lazy<Integer> MAX_RELATIONSHIP = Lazy.of(() -> Husbandry.HusbandryConfig.maxHappiness.get());
    private final ListMultimap<TraitType, IMobTrait> traits;
    protected final E entity;
    protected final Species species;
    private int happiness; //Current mob happiness
    private int happinessDivisor = 5; //Maximum happiness for this mob currently, increased by treats
    private int hunger; //How many days the mob has been without food
    private boolean hasProduct = true; ///If the mob has a product
    private int childhood;//How many days of childhood so far
    protected boolean loved; //If the mob has been loved today
    private boolean eaten; //If the mob has eaten today
    private final AbstractMobProductTrait products;
    private final LazyOptional<MobStats<?>> capability;
    private boolean domesticated;

    public MobStats(E entity, @Nonnull Species species) {
        this.entity = entity;
        this.species = species;
        this.traits = LinkedListMultimap.create();
        this.species.getTraits().forEach(trait -> {
            IMobTrait copy = trait instanceof IDataTrait ? ReflectionHelper.newInstance(trait.getClass()) : trait;
            if (copy instanceof IJoinWorldTrait) this.traits.get(TraitType.ON_JOIN).add(copy);
            if (copy instanceof IInteractiveTrait) this.traits.get(TraitType.ACTION).add(copy);
            if (copy instanceof IBiHourlyTrait) this.traits.get(TraitType.BI_HOURLY).add(copy);
            if (copy instanceof IDataTrait) this.traits.get(TraitType.DATA).add(copy);
            if (copy instanceof INewDayTrait) this.traits.get(TraitType.NEW_DAY).add(copy);
            if (copy instanceof IRenderTrait) this.traits.get(TraitType.RENDER).add(copy);
            if (copy instanceof IInitTrait) this.traits.get(TraitType.INIT).add(copy);
            if (copy instanceof IIconTrait) this.traits.get(TraitType.ICON).add(copy);
        });

        this.traits.get(TraitType.INIT).forEach(trait -> ((IInitTrait)trait).initTrait(this));
        this.products = (AbstractMobProductTrait) this.traits.get(TraitType.DATA).stream()
                .filter(t -> t instanceof AbstractMobProductTrait).findFirst().orElse(new MoreProductChanceTrait());
        this.capability = LazyOptional.of(() -> this);
    }

    @Override
    public E getEntity() {
        return entity;
    }

    @Override
    public Species getSpecies() {
        return species;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getTraits(TraitType type) {
        return (List<T>) traits.get(type);
    }

    @Override
    public void resetProduct() {
        entity.ate();
        hasProduct = true;
    }

    public void onNewDay() {
        List<INewDayTrait> traits = getTraits(TraitType.NEW_DAY);
        traits.forEach(trait -> trait.onNewDay(this));

        if (!eaten) {
            hunger++;
            decreaseHappiness(Husbandry.HusbandryConfig.hungerHappinessLoss.get());
        }

        loved = false;
        eaten = false;

        if (entity.isBaby()) {
            childhood++;

            if (childhood >= species.getDaysToMaturity()) {
                entity.setBaby(false);
                if (entity instanceof SlimeEntity) {
                    try {
                        ObfuscationReflectionHelper.findMethod(SlimeEntity.class, "func_70799_a", int.class, boolean.class).invoke(2, true);
                    } catch (IllegalAccessException | InvocationTargetException ignored) {}
                }
            }
        }

        //Force the mobs to execute important ai tasks?
        entity.goalSelector.getRunningGoals()
                .filter(ai -> ai.getGoal() instanceof AbstractMoveToBlockGoal)
                .forEach(ai -> ((AbstractMoveToBlockGoal) ai.getGoal()).resetRunTimer());
        PenguinNetwork.sendToNearby(new SendDataPacket(entity.getId(), this), entity);
    }

    @Override
    public boolean isDomesticated() {
        return domesticated;
    }

    @Override
    public int getHappiness() {
        return happiness;
    }

    @Override
    public void setHappiness(int happiness) {
        this.happiness = happiness;
        if (!entity.level.isClientSide)
            PenguinNetwork.sendToNearby(new SetHappinessPacket(entity.getId(), happiness), entity);
    }

    @Override
    public int getHappinessModifier() {
        return happinessDivisor;
    }

    @Override
    public void setHappinessModifier(int value) {
        happinessDivisor = value;
    }


    @Override
    public void decreaseHappiness(int happiness) {
        this.happiness = MathsHelper.constrainToRangeInt(this.happiness - happiness, 0, MAX_RELATIONSHIP.get() / happinessDivisor);
        if (!entity.level.isClientSide) {
            PenguinNetwork.sendToNearby(new SpawnHeartsPacket(entity.getId(), false), entity);
        }
    }

    @Override
    public void increaseHappiness(int happiness) {
        this.happiness = MathsHelper.constrainToRangeInt(this.happiness + happiness, 0, MAX_RELATIONSHIP.get() / happinessDivisor);
        if (!entity.level.isClientSide) {
            PenguinNetwork.sendToNearby(new SpawnHeartsPacket(entity.getId(), true), entity);
        }
    }

    /**
     * @return true if the event should be canceled
     */
    public boolean onEntityInteract(PlayerEntity player, Hand hand) {
        domesticated = true; //Interaction started, so mark them as domesticated, maybe make this more complicated later
        List<IInteractiveTrait> traits = getTraits(TraitType.ACTION);
        return traits.stream().anyMatch(trait ->
                trait.onRightClick(this, player, hand));
    }

    @Override
    public void feed() {
        hunger = 0;
        eaten = true;
    }

    @Override
    public int getHunger() {
        return hunger;
    }

    @Override
    public boolean isUnloved() {
        return !loved;
    }

    @Override
    public boolean setLoved() {
        this.loved = true;
        increaseHappiness(Husbandry.HusbandryConfig.lovedGain.get());
        return this.loved;
    }

    @Override
    public boolean canProduceProduct() {
        return hasProduct && !entity.isBaby();
    }

    @Override
    public void setProduced(int amount) {
        this.products.setProduced(this, amount);
        this.hasProduct = false;
    }

    @Override
    public boolean isHungry() {
        return !eaten;
    }

    @Override
    public int getMaxHearts() {
        return (MAX_RELATIONSHIP.get() / happinessDivisor) / (MAX_RELATIONSHIP.get()/10);
    }

    @Override
    public int getHearts() {
        return (int) ((((double) happiness) / MAX_RELATIONSHIP.get()) * 10); //0 > 10
    }

    @Override
    public void setHearts(int hearts) {
        hearts = Math.max(0, Math.min(10, hearts));
        this.happiness = hearts * (MAX_RELATIONSHIP.get()/ 10);
        if (!entity.level.isClientSide)
            PenguinNetwork.sendToNearby(new SetHappinessPacket(entity.getId(), happiness), entity);
    }

    @Override
    public int getMaxHappiness() {
        return MAX_RELATIONSHIP.get();
    }

    @Nullable
    public static MobStats<?> getStats(Entity entity) {
        LazyOptional<MobStats<?>> stats = entity.getCapability(MOB_STATS_CAPABILITY);
        return stats.isPresent() && stats.resolve().isPresent() ? stats.resolve().get() : null;
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, final @Nullable Direction side) {
        return cap == CapabilityStatsHandler.MOB_STATS_CAPABILITY ? capability.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putBoolean("Domesticated", domesticated);
        tag.putInt("Happiness", happiness);
        tag.putInt("HappinessDivisor", happinessDivisor);
        tag.putInt("Hunger", hunger);
        tag.putInt("Childhood", childhood);
        tag.putBoolean("HasProduct", hasProduct);
        tag.putBoolean("Loved", loved);
        tag.putBoolean("Eaten", eaten);
        List<IDataTrait> data = getTraits(TraitType.DATA);
        data.forEach(d -> d.save(tag));
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        domesticated = nbt.getBoolean("Domesticated");
        happiness = nbt.getInt("Happiness");
        happinessDivisor = nbt.getInt("HappinessDivisor");
        if (happinessDivisor <= 0 || happinessDivisor > 5) happinessDivisor = 5; //Fix the divisor
        hunger = nbt.getInt("Hunger");
        childhood = nbt.getInt("Childhood");
        hasProduct = nbt.getBoolean("HasProduct");
        loved = nbt.getBoolean("Loved");
        eaten = nbt.getBoolean("Eaten");
        List<IDataTrait> data = getTraits(TraitType.DATA);
        data.forEach(d -> d.load(nbt));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MobStats<?> that = (MobStats<?>) o;
        return Objects.equals(entity.getUUID(), that.entity.getUUID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity.getUUID());
    }
}
