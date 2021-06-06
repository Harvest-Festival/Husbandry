package uk.joshiejack.husbandry.animals.traits.lifestyle;

import net.minecraft.nbt.CompoundNBT;
import uk.joshiejack.husbandry.animals.traits.AbstractAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IDataTrait;

public class PetTrait extends AbstractAnimalTrait implements IDataTrait {
    private int skill;

    public PetTrait(String name) {
        super(name);
    }

    @Override
    public void save(CompoundNBT tag) {
        tag.putInt("Skill", skill);
    }

    @Override
    public void load(CompoundNBT nbt) {
        skill = nbt.getInt("Skill");
    }
}
