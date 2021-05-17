package uk.joshiejack.husbandry.animals.traits.product;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.ai.goal.Goal;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IGoalTrait;
import uk.joshiejack.husbandry.entity.ai.DropsProductGoal;
import uk.joshiejack.penguinlib.util.PenguinLoader;

@PenguinLoader("drops_product")
public class DropsProductTrait extends AnimalTrait implements IGoalTrait {
    public DropsProductTrait(String name) {
        super(name);
    }

    @Override
    public int getPriority() {
        return 4;
    }

    @Override
    public Goal getGoal(AgeableEntity ageable, AnimalStats<?> stats) {
        return new DropsProductGoal(ageable, stats);
    }
}
