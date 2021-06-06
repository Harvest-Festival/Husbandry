package uk.joshiejack.husbandry.animals.traits.lifestyle;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import uk.joshiejack.husbandry.animals.AnimalSpecies;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AbstractAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IDataTrait;
import uk.joshiejack.husbandry.animals.traits.types.INewDayTrait;
import uk.joshiejack.penguinlib.util.helpers.generic.MathsHelper;

public class MortalTrait extends AbstractAnimalTrait implements INewDayTrait, IDataTrait {
    private static final DamageSource OLD_AGE = new DamageSource("oldage");
    private static final int DEATH_CHANCE = 360;
    private int age;

    public MortalTrait(String name) {
        super(name);
    }

    @Override
    public void onNewDay(AnimalStats<?> stats) {
        int chance = MathsHelper.constrainToRangeInt(DEATH_CHANCE, 1, Short.MAX_VALUE);
        AnimalSpecies species = stats.getSpecies();
        AgeableEntity entity = stats.getEntity();
        if (age >= species.getMaxAge() || (age >= species.getMinAge() && entity.getRandom().nextInt(chance) == 0)) {
            entity.hurt(OLD_AGE, Integer.MAX_VALUE);
        }
    }


    @Override
    public void load(CompoundNBT nbt) {
        age = nbt.getInt("Age");
    }

    @Override
    public void save(CompoundNBT nbt) {
        nbt.putInt("Age", age);
    }
}
