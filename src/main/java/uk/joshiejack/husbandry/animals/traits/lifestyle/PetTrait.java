package uk.joshiejack.husbandry.animals.traits.lifestyle;

import net.minecraft.nbt.CompoundNBT;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IDataTrait;
import uk.joshiejack.penguinlib.util.PenguinLoader;

@PenguinLoader("pet")
public class PetTrait extends AnimalTrait implements IDataTrait {
    private int skill;

    public PetTrait(String name) {
        super(name);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("Skill", skill);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        skill = nbt.getInt("Skill");
    }
}
