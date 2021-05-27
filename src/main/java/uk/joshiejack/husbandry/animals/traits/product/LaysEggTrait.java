package uk.joshiejack.husbandry.animals.traits.product;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.passive.ChickenEntity;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IGoalTrait;
import uk.joshiejack.husbandry.entity.ai.LayEggGoal;

public class LaysEggTrait extends AnimalTrait implements IGoalTrait {
    public LaysEggTrait(String name) {
        super(name);
    }

    @Override
    public void modifyGoals(AnimalStats<?> stats, GoalSelector selector) {
        if (stats.getEntity() instanceof ChickenEntity) {
            ((ChickenEntity) stats.getEntity()).eggTime = Integer.MAX_VALUE;
        }

        selector.addGoal(4, new LayEggGoal(stats.getEntity(), stats));
    }
}
