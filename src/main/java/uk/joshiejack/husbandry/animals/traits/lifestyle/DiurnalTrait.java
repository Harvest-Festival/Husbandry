package uk.joshiejack.husbandry.animals.traits.lifestyle;

import net.minecraft.entity.ai.goal.GoalSelector;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IGoalTrait;
import uk.joshiejack.husbandry.entity.ai.ShelterAtNightGoal;

public class DiurnalTrait extends AnimalTrait implements IGoalTrait {
    public DiurnalTrait(String name) {
        super(name);
    }

    @Override
    public void modifyGoals(AnimalStats<?> stats, GoalSelector selector) {
        selector.addGoal(4, new ShelterAtNightGoal(stats.getEntity(), stats));
    }
}