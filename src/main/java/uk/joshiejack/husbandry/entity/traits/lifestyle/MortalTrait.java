package uk.joshiejack.husbandry.entity.traits.lifestyle;

import net.minecraft.entity.MobEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.ISpecies;
import uk.joshiejack.husbandry.api.trait.IDataTrait;
import uk.joshiejack.husbandry.api.trait.INewDayTrait;
import uk.joshiejack.penguinlib.util.helpers.generic.MathsHelper;

public class MortalTrait implements INewDayTrait, IDataTrait {
    private static final DamageSource OLD_AGE = new DamageSource("oldage");
    private static final int DEATH_CHANCE = 360;
    private int age;

    @Override
    public void onNewDay(IMobStats<?> stats) {
        int chance = MathsHelper.constrainToRangeInt(DEATH_CHANCE, 1, Short.MAX_VALUE);
        ISpecies species = stats.getSpecies();
        MobEntity entity = stats.getEntity();
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
