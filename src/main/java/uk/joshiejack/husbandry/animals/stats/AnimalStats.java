package uk.joshiejack.husbandry.animals.stats;

import com.google.common.collect.Maps;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.HusbandryConfig;
import uk.joshiejack.husbandry.animals.AnimalSpecies;
import uk.joshiejack.husbandry.animals.traits.product.AnimalTraitProduct;
import uk.joshiejack.husbandry.animals.traits.types.*;
import uk.joshiejack.husbandry.entity.ai.AbstractMoveToBlockGoal;
import uk.joshiejack.husbandry.item.HusbandryItems;
import uk.joshiejack.husbandry.network.SendDataPacket;
import uk.joshiejack.husbandry.network.SpawnHeartsPacket;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.util.helpers.generic.MathsHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static uk.joshiejack.husbandry.animals.stats.CapabilityStatsHandler.ANIMAL_STATS_CAPABILITY;

public class AnimalStats<E extends AgeableEntity> implements ICapabilityProvider, INBTSerializable<CompoundNBT> {
    public static final ITag.INamedTag<Item> TREATS = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "treat"));
    private static final int MAX_RELATIONSHIP = 30000;
    private static final int MIN_MEDIUM = 2;
    private static final int MIN_LARGE = 5;
    private static final DamageSource OLD_AGE = new DamageSource("oldage");
    protected final E entity;
    protected final AnimalSpecies type;
    private int town;
    private int age; //Current animal age
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
    private boolean wasOutsideInSun; //If the animal was outside last time
    private boolean annoyed; //If the player has insulted the animal today
    private final Map<String, IDataTrait> data;
    private final AnimalTraitProduct products;
    private final LazyOptional<AnimalStats<?>> capability;

    public AnimalStats(E entity, @Nonnull AnimalSpecies type) {
        this.entity = entity;
        this.type = type;
        this.data = Maps.newHashMap();
        List<IDataTrait> traits = type.getTraits(AnimalTraits.Type.DATA);
        traits.forEach(trait -> {
            try {
                data.put(trait.getSerializedName(), trait.getClass().getConstructor(String.class).newInstance(trait.getSerializedName()));
            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
            }
        });

        this.data.values().forEach(data -> data.initTrait(this));
        this.products = (AnimalTraitProduct) this.data.values().stream().filter(t -> t instanceof AnimalTraitProduct).findFirst().orElse(null);
        if (entity != null) {
            //TODO: TOWN? this.town = AnimalTownController.getTown(entity);
        }

        this.capability = LazyOptional.of(() -> this);
    }

    public E getEntity() {
        return entity;
    }

    public AnimalSpecies getType() {
        return type;
    }

    public int getTown() {
        return town;
    }

    public void onBihourlyTick() {
        World world = entity.level;
        BlockPos pos = entity.blockPosition();
        boolean dayTime = world.isDay();
        boolean isRaining = world.isRaining();
        boolean isOutside = world.canSeeSky(pos);
        boolean isOutsideInSun = !isRaining && isOutside && dayTime && !world.getBiome(pos).shouldSnow(world, pos);
        if (isOutsideInSun && wasOutsideInSun) {
            increaseHappiness(2);
        }

        //Mark the past value
        wasOutsideInSun = isOutsideInSun;
        List<IBiHourlyTrait> traits = type.getTraits(AnimalTraits.Type.BI_HOURLY);
        traits.forEach(trait -> trait.onBihourlyTick(this));
    }

    public void resetProduct() {
        entity.ate();
        hasProduct = true;
    }

    public void onNewDay() {
        int chance = MathsHelper.constrainToRangeInt(HusbandryConfig.deathChance, 1, Short.MAX_VALUE);
        if (age >= type.getMaxAge() || (age >= type.getMinAge() && entity.getRandom().nextInt(chance) == 0)) {
            entity.hurt(OLD_AGE, Integer.MAX_VALUE);
        }

        List<INewDayTrait> traits = type.getTraits(AnimalTraits.Type.NEW_DAY);
        traits.forEach(trait -> trait.onNewDay(this));

        if (!eaten) {
            hunger++;
            decreaseHappiness(1);
        }

        if (happinessDivisor > 1 && genericTreatsGiven >= type.getGenericTreats() && speciesTreatsGiven >= type.getSpeciesTreats()) {
            genericTreatsGiven -= type.getGenericTreats();
            speciesTreatsGiven -= type.getSpeciesTreats();
            adjustHappinessDivisor(-1);
        }

        treated = false;
        loved = false;
        eaten = false;
        annoyed = false;

        if (entity.isBaby()) {
            childhood++;

            if (childhood >= type.getDaysToMaturity()) {
                entity.setAge(0); //Grow up!
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
        List<IInteractiveTrait> traits = type.getTraits(AnimalTraits.Type.ACTION);
        return canTreat(player, hand) || traits.stream().anyMatch(trait ->
                trait.onRightClick(this, player, hand));
    }

    private boolean canTreat(PlayerEntity player, Hand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (!TREATS.contains(held.getItem())) return false;
        boolean generic = held.getItem() == HusbandryItems.GENERIC_TREAT.get();
        if (!generic) { //Feeding the wrong treat will upset them!
            if (type.getTreat() != held.getItem() && !annoyed) {
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

    public boolean hasBeenLoved() {
        return loved;
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

    public boolean hasEaten() {
        return eaten;
    }

    public List<ItemStack> getProduct(PlayerEntity player) {
        return type.getProducts().getProduct(entity, player);
    }

    public int getHearts() {
        return (int) ((((double) happiness) / MAX_RELATIONSHIP) * 9); //0 > 9
    }

    public int getMaxRelationship() {
        return MAX_RELATIONSHIP;
    }

    public int getMinMedium() {
        return MIN_MEDIUM;
    }

    public int getMinLarge() {
        return MIN_LARGE;
    }

    /**
     * @return a value from 0 to 2
     **/
    public static int getProductSize(Random random, int happiness) {
        int hearts = 1 + (int) ((((double) happiness) / MAX_RELATIONSHIP) * 9);
        int largeChance = (100 / hearts) - 9;
        int mediumChance = (50 / hearts) - 4;
        if (hearts >= 5 && random.nextInt(largeChance) == 0) return 2;
        else if (hearts >= 2 && random.nextInt(mediumChance) == 0) return 1;
        else return 0;
    }

    @Nullable
    public static AnimalStats<?> getStats(Entity entity) {
        LazyOptional<AnimalStats<?>> stats = entity.getCapability(ANIMAL_STATS_CAPABILITY);
        return stats.isPresent() && stats.resolve().isPresent() ? stats.resolve().get() : null;
    }

    public boolean hasTrait(String trait) {
        return data.containsKey(trait) || type.hasTrait(trait);
    }

    @SuppressWarnings("unchecked")
    public <T extends IDataTrait> T getTrait(String trait) {
        return (T) data.get(trait);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, final @Nullable Direction side) {
        return cap == CapabilityStatsHandler.ANIMAL_STATS_CAPABILITY ? capability.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("Town", town);
        tag.putInt("Age", age);
        tag.putInt("Happiness", happiness);
        tag.putInt("HappinessDivisor", happinessDivisor);
        tag.putInt("Hunger", hunger);
        tag.putInt("Childhood", childhood);
        tag.putBoolean("HasProduct", hasProduct);
        tag.putBoolean("Loved", loved);
        tag.putBoolean("Eaten", eaten);
        tag.putBoolean("Special", special);
        tag.putBoolean("InSun", wasOutsideInSun);
        tag.putBoolean("Treated", treated);
        tag.putBoolean("Annoyed", annoyed);
        tag.putInt("GenericTreats", genericTreatsGiven);
        tag.putInt("TypeTreats", speciesTreatsGiven);
        CompoundNBT traits = new CompoundNBT();
        for (Map.Entry<String, IDataTrait> entry : this.data.entrySet()) {
            traits.put(entry.getKey(), entry.getValue().serializeNBT());
        }

        tag.put("Traits", traits);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        town = nbt.getInt("Town");
        age = nbt.getInt("Age");
        happiness = nbt.getInt("Happiness");
        happinessDivisor = nbt.getInt("HappinessDivisor");
        if (happinessDivisor == 0) happinessDivisor = 5; //Fix the divisor
        hunger = nbt.getInt("Hunger");
        childhood = nbt.getInt("Childhood");
        hasProduct = nbt.getBoolean("HasProduct");
        loved = nbt.getBoolean("Loved");
        eaten = nbt.getBoolean("Eaten");
        special = nbt.getBoolean("Special");
        wasOutsideInSun = nbt.getBoolean("InSun");
        treated = nbt.getBoolean("Treated");
        genericTreatsGiven = nbt.getInt("GenericTreats");
        speciesTreatsGiven = nbt.getInt("TypeTreats");
        annoyed = nbt.getBoolean("Annoyed");
        CompoundNBT traits = nbt.getCompound("Traits");
        for (Map.Entry<String, IDataTrait> entry : this.data.entrySet()) {
            entry.getValue().deserializeNBT(traits.getCompound(entry.getKey()));
        }
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
