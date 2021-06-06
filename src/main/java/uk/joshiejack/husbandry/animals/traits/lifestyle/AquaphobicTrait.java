package uk.joshiejack.husbandry.animals.traits.lifestyle;

import net.minecraft.entity.ai.goal.GoalSelector;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AbstractAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IGoalTrait;
import uk.joshiejack.husbandry.entity.ai.HideFromRainGoal;

public class AquaphobicTrait extends AbstractAnimalTrait implements IGoalTrait {
    public AquaphobicTrait(String name) {
        super(name);
    }

    @Override
    public void modifyGoals(AnimalStats<?> stats, GoalSelector selector) {
        selector.addGoal(4, new HideFromRainGoal(stats.getEntity(), stats));
    }
}