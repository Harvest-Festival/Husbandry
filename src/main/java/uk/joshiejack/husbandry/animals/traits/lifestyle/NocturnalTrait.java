package uk.joshiejack.husbandry.animals.traits.lifestyle;

import net.minecraft.entity.ai.goal.GoalSelector;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AbstractAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IGoalTrait;
import uk.joshiejack.husbandry.entity.ai.HideFromSunGoal;

public class NocturnalTrait extends AbstractAnimalTrait implements IGoalTrait {
    public NocturnalTrait(String name) {
        super(name);
    }

    @Override
    public void modifyGoals(AnimalStats<?> stats, GoalSelector selector) {
        selector.addGoal(4, new HideFromSunGoal(stats.getEntity(), stats));
    }
}