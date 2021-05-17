package uk.joshiejack.husbandry.animals.traits.types;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.ai.goal.Goal;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;

public interface IGoalTrait extends AnimalTraits {
    Goal getGoal(AgeableEntity ageable, AnimalStats<?> stats);

    default int getPriority() {
        return 5;
    }
}
