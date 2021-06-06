package uk.joshiejack.husbandry.entity.traits.lifestyle;

import net.minecraft.nbt.CompoundNBT;
import uk.joshiejack.husbandry.api.trait.AbstractMobTrait;
import uk.joshiejack.husbandry.api.trait.IDataTrait;

public class PetTrait extends AbstractMobTrait implements IDataTrait {
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
