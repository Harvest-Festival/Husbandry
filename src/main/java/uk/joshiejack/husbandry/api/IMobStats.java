package uk.joshiejack.husbandry.api;

import net.minecraft.entity.MobEntity;

public interface IMobStats<E extends MobEntity> {
    E getEntity();

    ISpecies getSpecies();

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

    int getHearts();

    int getMaxRelationship();

    void resetProduct();
}
