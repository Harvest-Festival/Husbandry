package uk.joshiejack.husbandry.animals.traits.types;

import net.minecraft.entity.ai.goal.GoalSelector;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;

public interface IGoalTrait extends IAnimalTrait {
    void modifyGoals(AnimalStats<?> stats, GoalSelector selector);
}
