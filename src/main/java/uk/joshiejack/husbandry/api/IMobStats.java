package uk.joshiejack.husbandry.api;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import uk.joshiejack.husbandry.api.trait.TraitType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public interface IMobStats<E extends MobEntity> {
    E getEntity();

    ISpecies getSpecies();

    <T> Stream<T> getTraits(TraitType type);

    int getHappiness();

    void decreaseHappiness(int happiness);

    void increaseHappiness(int happiness);

    void feed();

    int getHunger();

    boolean isUnloved();

    boolean setLoved();

    boolean canProduceProduct();

    void setProduced(int amount);

    boolean isHungry();

    List<ItemStack> getProduct(@Nullable PlayerEntity player);

    int getHearts();

    int getMaxRelationship();

    <T> T getTraitByName(String trait);

    void resetProduct();
}
