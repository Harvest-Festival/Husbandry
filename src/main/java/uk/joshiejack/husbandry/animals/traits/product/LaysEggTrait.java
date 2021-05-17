package uk.joshiejack.husbandry.animals.traits.product;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.ChickenEntity;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IGoalTrait;
import uk.joshiejack.husbandry.entity.ai.LayEggGoal;
import uk.joshiejack.penguinlib.util.PenguinLoader;

@PenguinLoader("lays_egg")
public class LaysEggTrait extends AnimalTrait implements IGoalTrait {
    public LaysEggTrait(String name) {
        super(name);
    }

    @Override
    public int getPriority() {
        return 4;
    }

    @Override
    public Goal getGoal(AgeableEntity ageable, AnimalStats<?> stats) {
        //Force the egg timer to max value
        if (ageable instanceof ChickenEntity) {
            ((ChickenEntity) ageable).eggTime = Integer.MAX_VALUE;
        }

        return new LayEggGoal(ageable, stats);
    }
}
