package uk.joshiejack.husbandry.animals.traits.product;

import net.minecraft.entity.ai.goal.GoalSelector;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AbstractAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IGoalTrait;
import uk.joshiejack.husbandry.entity.ai.FindProductGoal;

public class FindsProductTrait extends AbstractAnimalTrait implements IGoalTrait {
    public FindsProductTrait(String name) {
        super(name);
    }

    @Override
    public void modifyGoals(AnimalStats<?> stats, GoalSelector selector) {
        selector.addGoal(4, new FindProductGoal(stats.getEntity(), stats));
    }
}
