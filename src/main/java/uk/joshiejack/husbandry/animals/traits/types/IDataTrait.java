package uk.joshiejack.husbandry.animals.traits.types;

import net.minecraft.nbt.CompoundNBT;

public interface IDataTrait extends IAnimalTrait {
    void load(CompoundNBT nbt);

    void save(CompoundNBT nbt);
}
