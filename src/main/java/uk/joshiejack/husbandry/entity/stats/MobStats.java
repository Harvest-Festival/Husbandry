package uk.joshiejack.husbandry.entity.stats;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.*;
import uk.joshiejack.husbandry.entity.ai.AbstractMoveToBlockGoal;
import uk.joshiejack.husbandry.entity.traits.product.AbstractMobProductTrait;
import uk.joshiejack.husbandry.item.HusbandryItems;
import uk.joshiejack.husbandry.network.SendDataPacket;
import uk.joshiejack.husbandry.network.SpawnHeartsPacket;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.util.helpers.generic.MathsHelper;
import uk.joshiejack.penguinlib.util.helpers.generic.ReflectionHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.stream.Stream;

import static uk.joshiejack.husbandry.entity.stats.CapabilityStatsHandler.MOB_STATS_CAPABILITY;

public class MobStats<E extends MobEntity> implements ICapabilityProvider, INBTSerializable<CompoundNBT>, IMobStats<E> {
    public static final ITag.INamedTag<Item> TREATS = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "treat"));
    private static final int MAX_RELATIONSHIP = 30000;
    private final Multimap<TraitType, IMobTrait> traits;
    protected final E entity;
    protected final Species species;
    private int town;
    private int happiness; //Current mob happiness
    private int happinessDivisor = 5; //Maximum happiness for this mob currently, increased by treats
    private int hunger; //How many days the mob has been without food
    private boolean hasProduct = true; ///If the mob has a product
    private boolean treated; //If the mob has been treated
    private int genericTreatsGiven;
    private int speciesTreatsGiven;
    private int childhood;//How many days of childhood so far
    protected boolean loved; //If the mob has been loved today
    private boolean eaten; //If the mob has eaten today
    private boolean special; //If the mob is special
    private boolean annoyed; //If the player has insulted the mob today
    private final AbstractMobProductTrait products;
    private final LazyOptional<MobStats<?>> capability;

    public MobStats(E entity, @Nonnull Species species) {
        this.entity = entity;
        this.species = species;
        this.traits = HashMultimap.create();
        this.species.getTraits().forEach(trait -> {
            IMobTrait copy = trait instanceof IDataTrait ? ReflectionHelper.newInstance(trait.getClass()) : trait;
            if (copy instanceof IJoinWorldTrait) this.traits.get(TraitType.ON_JOIN).add(copy);
            if (copy instanceof IInteractiveTrait) this.traits.get(TraitType.ACTION).add(copy);
            if (copy instanceof IBiHourlyTrait) this.traits.get(TraitType.BI_HOURLY).add(copy);
            if (copy instanceof IDataTrait) this.traits.get(TraitType.DATA).add(copy);
            if (copy instanceof INewDayTrait) this.traits.get(TraitType.NEW_DAY).add(copy);
            if (copy instanceof IDisplayTrait) this.traits.get(TraitType.DISPLAY).add(copy);
            if (copy instanceof IInitTrait) this.traits.get(TraitType.INIT).add(copy);
        });

        this.traits.get(TraitType.INIT).forEach(trait -> ((IInitTrait)trait).initTrait(this));
        this.products = (AbstractMobProductTrait) this.traits.get(TraitType.DATA).stream().filter(t -> t instanceof AbstractMobProductTrait).findFirst().orElse(null);
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
    public <T> Stream<T> getTraits(TraitType type) {
        return (Stream<T>) traits.get(type).stream();
    }

    @Override
    public void resetProduct() {
        entity.ate();
        hasProduct = true;
    }

    public void onNewDay() {
        Stream<INewDayTrait> traits = getTraits(TraitType.NEW_DAY);
        traits.forEach(trait -> trait.onNewDay(this));

        if (!eaten) {
            hunger++;
            decreaseHappiness(1);
        }

        if (happinessDivisor > 1 && genericTreatsGiven >= species.getGenericTreats() && speciesTreatsGiven >= species.getSpeciesTreats()) {
            genericTreatsGiven -= species.getGenericTreats();
            speciesTreatsGiven -= species.getSpeciesTreats();
            happinessDivisor += -1;
        }

        treated = false;
        loved = false;
        eaten = false;
        annoyed = false;

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
    public int getHappiness() {
        return happiness;
    }

    @Override
    public void decreaseHappiness(int happiness) {
        this.happiness = MathsHelper.constrainToRangeInt(this.happiness - happiness, 0, MAX_RELATIONSHIP / happinessDivisor);
        if (!entity.level.isClientSide) {
            PenguinNetwork.sendToNearby(new SpawnHeartsPacket(entity.getId(), false), entity);
        }
    }

    @Override
    public void increaseHappiness(int happiness) {
        this.happiness = MathsHelper.constrainToRangeInt(this.happiness + happiness, 0, MAX_RELATIONSHIP / happinessDivisor);
        if (!entity.level.isClientSide) {
            PenguinNetwork.sendToNearby(new SpawnHeartsPacket(entity.getId(), true), entity);
        }
    }

    /**
     * @return true if the event should be canceled
     */
    public boolean onEntityInteract(PlayerEntity player, Hand hand) {
        Stream<IInteractiveTrait> traits = getTraits(TraitType.ACTION);
        return canTreat(player, hand) || traits.anyMatch(trait ->
                trait.onRightClick(this, player, hand));
    }

    private boolean canTreat(PlayerEntity player, Hand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (!TREATS.contains(held.getItem())) return false;
        boolean generic = held.getItem() == HusbandryItems.GENERIC_TREAT.get();
        if (!generic) { //Feeding the wrong treat will upset them!
            if (species.getTreat() != held.getItem() && !annoyed) {
                annoyed = true;
                decreaseHappiness(500);
                held.shrink(1);
                return true;
            }
        }

        //Feeding the correct treat makes them happy
        //But only if they haven't been fed already today
        if (!treated) {
            if (generic) {
                genericTreatsGiven++;
            } else speciesTreatsGiven++;

            held.shrink(1); //Remove it
            increaseHappiness(generic ? 250 : 100);
            treated = true;
            return true;
        }

        return false;
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
        increaseHappiness(100);
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
    public int getHearts() {
        return (int) ((((double) happiness) / MAX_RELATIONSHIP) * 10); //0 > 9
    }

    @Override
    public int getMaxRelationship() {
        return MAX_RELATIONSHIP;
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
        tag.putInt("Town", town);
        tag.putInt("Happiness", happiness);
        tag.putInt("HappinessDivisor", happinessDivisor);
        tag.putInt("Hunger", hunger);
        tag.putInt("Childhood", childhood);
        tag.putBoolean("HasProduct", hasProduct);
        tag.putBoolean("Loved", loved);
        tag.putBoolean("Eaten", eaten);
        tag.putBoolean("Special", special);
        tag.putBoolean("Treated", treated);
        tag.putBoolean("Annoyed", annoyed);
        tag.putInt("GenericTreats", genericTreatsGiven);
        tag.putInt("TypeTreats", speciesTreatsGiven);
        Stream<IDataTrait> data = getTraits(TraitType.DATA);
        data.forEach(d -> d.save(tag));
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        town = nbt.getInt("Town");
        happiness = nbt.getInt("Happiness");
        happinessDivisor = nbt.getInt("HappinessDivisor");
        if (happinessDivisor == 0) happinessDivisor = 5; //Fix the divisor
        hunger = nbt.getInt("Hunger");
        childhood = nbt.getInt("Childhood");
        hasProduct = nbt.getBoolean("HasProduct");
        loved = nbt.getBoolean("Loved");
        eaten = nbt.getBoolean("Eaten");
        special = nbt.getBoolean("Special");
        treated = nbt.getBoolean("Treated");
        genericTreatsGiven = nbt.getInt("GenericTreats");
        speciesTreatsGiven = nbt.getInt("TypeTreats");
        annoyed = nbt.getBoolean("Annoyed");
        Stream<IDataTrait> data = getTraits(TraitType.DATA);
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
