package uk.joshiejack.husbandry.animals.stats;

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
import uk.joshiejack.husbandry.animals.AnimalSpecies;
import uk.joshiejack.husbandry.animals.traits.product.AbstractAnimalTraitProduct;
import uk.joshiejack.husbandry.animals.traits.types.*;
import uk.joshiejack.husbandry.entity.ai.AbstractMoveToBlockGoal;
import uk.joshiejack.husbandry.item.HusbandryItems;
import uk.joshiejack.husbandry.network.SendDataPacket;
import uk.joshiejack.husbandry.network.SpawnHeartsPacket;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.util.helpers.generic.MathsHelper;
import uk.joshiejack.penguinlib.util.helpers.generic.ReflectionHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static uk.joshiejack.husbandry.animals.stats.CapabilityStatsHandler.ANIMAL_STATS_CAPABILITY;

public class AnimalStats<E extends MobEntity> implements ICapabilityProvider, INBTSerializable<CompoundNBT> {
    public static final ITag.INamedTag<Item> TREATS = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "treat"));
    private static final int MAX_RELATIONSHIP = 30000;
    private final Multimap<IAnimalTrait.Type, IAnimalTrait> traits;
    protected final E entity;
    protected final AnimalSpecies species;
    private int town;
    private int happiness; //Current animal happiness
    private int happinessDivisor = 5; //Maximum happiness for this animal currently, increased by treats
    private int hunger; //How many days the animal has been without food
    private boolean hasProduct = true; ///If the animal has a product
    private boolean treated; //If the animal has been treated
    private int genericTreatsGiven;
    private int speciesTreatsGiven;
    private int childhood;//How many days of childhood so far
    protected boolean loved; //If the animal has been loved today
    private boolean eaten; //If the animal has eaten today
    private boolean special; //If the animal is special
    private boolean annoyed; //If the player has insulted the animal today
    private final AbstractAnimalTraitProduct products;
    private final LazyOptional<AnimalStats<?>> capability;

    public AnimalStats(E entity, @Nonnull AnimalSpecies species) {
        this.entity = entity;
        this.species = species;
        this.traits = HashMultimap.create();
        this.species.getTraits().forEach(trait -> {
            IAnimalTrait copy = trait instanceof IDataTrait ? ReflectionHelper.newInstance(ReflectionHelper.getConstructor(trait.getClass(), String.class), trait.getSerializedName()) : trait;
            if (copy instanceof IJoinWorldTrait) this.traits.get(IAnimalTrait.Type.ON_JOIN).add(copy);
            if (copy instanceof IInteractiveTrait) this.traits.get(IAnimalTrait.Type.ACTION).add(copy);
            if (copy instanceof IBiHourlyTrait) this.traits.get(IAnimalTrait.Type.BI_HOURLY).add(copy);
            if (copy instanceof IDataTrait) this.traits.get(IAnimalTrait.Type.DATA).add(copy);
            if (copy instanceof INewDayTrait) this.traits.get(IAnimalTrait.Type.NEW_DAY).add(copy);
            if (copy instanceof IDisplayTrait) this.traits.get(IAnimalTrait.Type.DISPLAY).add(copy);
        });

        this.traits.get(IAnimalTrait.Type.DATA).forEach(trait -> trait.initTrait(this));
        this.products = (AbstractAnimalTraitProduct) this.traits.get(IAnimalTrait.Type.DATA).stream().filter(t -> t instanceof AbstractAnimalTraitProduct).findFirst().orElse(null);
        this.capability = LazyOptional.of(() -> this);
    }

    public E getEntity() {
        return entity;
    }

    public AnimalSpecies getSpecies() {
        return species;
    }

    @SuppressWarnings("unchecked")
    public <T extends IAnimalTrait> Stream<T> getTraits(IAnimalTrait.Type type) {
        return (Stream<T>) traits.get(type).stream();
    }

    public void onBihourlyTick() {
        Stream<IBiHourlyTrait> traits = getTraits(IAnimalTrait.Type.BI_HOURLY);
        traits.forEach(trait -> trait.onBihourlyTick(this));
    }

    public void resetProduct() {
        entity.ate();
        hasProduct = true;
    }

    public void onNewDay() {
        Stream<INewDayTrait> traits = getTraits(IAnimalTrait.Type.NEW_DAY);
        traits.forEach(trait -> trait.onNewDay(this));

        if (!eaten) {
            hunger++;
            decreaseHappiness(1);
        }

        if (happinessDivisor > 1 && genericTreatsGiven >= species.getGenericTreats() && speciesTreatsGiven >= species.getSpeciesTreats()) {
            genericTreatsGiven -= species.getGenericTreats();
            speciesTreatsGiven -= species.getSpeciesTreats();
            adjustHappinessDivisor(-1);
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

        //Force the animals to execute important ai tasks?
        entity.goalSelector.getRunningGoals()
                .filter(ai -> ai.getGoal() instanceof AbstractMoveToBlockGoal)
                .forEach(ai -> ((AbstractMoveToBlockGoal) ai.getGoal()).resetRunTimer());
        PenguinNetwork.sendToNearby(new SendDataPacket(entity.getId(), this), entity);
    }

    public void adjustHappinessDivisor(int amount) {
        happinessDivisor += amount;
    }

    public int getHappiness() {
        return happiness;
    }

    public void decreaseHappiness(int happiness) {
        this.happiness = MathsHelper.constrainToRangeInt(this.happiness - happiness, 0, MAX_RELATIONSHIP / happinessDivisor);
        if (!entity.level.isClientSide) {
            PenguinNetwork.sendToNearby(new SpawnHeartsPacket(entity.getId(), false), entity);
        }
    }

    public void increaseHappiness(int happiness) {
        this.happiness = MathsHelper.constrainToRangeInt(this.happiness + happiness, 0, MAX_RELATIONSHIP / happinessDivisor);
        if (!entity.level.isClientSide) {
            PenguinNetwork.sendToNearby(new SpawnHeartsPacket(entity.getId(), true), entity);
        }
    }

    /**
     * @return true if the event should be canceled
     */
    public boolean onRightClick(PlayerEntity player, Hand hand) {
        Stream<IInteractiveTrait> traits = getTraits(IAnimalTrait.Type.ACTION);
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

    public void feed() {
        hunger = 0;
        eaten = true;
    }

    public int getHunger() {
        return hunger;
    }

    public boolean isUnloved() {
        return !loved;
    }

    public boolean setLoved() {
        this.loved = true;
        increaseHappiness(100);
        return this.loved;
    }

    public boolean canProduceProduct() {
        return hasProduct && !entity.isBaby();
    }

    public void setProduced(int amount) {
        this.products.setProduced(this, amount);
        this.hasProduct = false;
    }

    public boolean isHungry() {
        return !eaten;
    }

    public List<ItemStack> getProduct(@Nullable PlayerEntity player) {
        return species.getProducts().getProduct(entity, player);
    }

    public int getHearts() {
        return (int) ((((double) happiness) / MAX_RELATIONSHIP) * 10); //0 > 9
    }

    public int getMaxRelationship() {
        return MAX_RELATIONSHIP;
    }

    @Nullable
    public static AnimalStats<?> getStats(Entity entity) {
        LazyOptional<AnimalStats<?>> stats = entity.getCapability(ANIMAL_STATS_CAPABILITY);
        return stats.isPresent() && stats.resolve().isPresent() ? stats.resolve().get() : null;
    }

    @SuppressWarnings("unchecked")
    public <T extends IDataTrait> T getTrait(String trait) {
        return (T) traits.get(IAnimalTrait.Type.DATA).stream().filter(entry -> entry.getSerializedName().equals(trait)).findFirst().get();
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, final @Nullable Direction side) {
        return cap == CapabilityStatsHandler.ANIMAL_STATS_CAPABILITY ? capability.cast() : LazyOptional.empty();
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
        Stream<IDataTrait> data = getTraits(IAnimalTrait.Type.DATA);
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
        Stream<IDataTrait> data = getTraits(IAnimalTrait.Type.DATA);
        data.forEach(d -> d.load(nbt));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalStats<?> that = (AnimalStats<?>) o;
        return Objects.equals(entity.getUUID(), that.entity.getUUID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity.getUUID());
    }
}
