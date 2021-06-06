package uk.joshiejack.husbandry.entity.traits.product;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.passive.ChickenEntity;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.ai.LayEggGoal;
import uk.joshiejack.husbandry.entity.ai.RoostGoal;

public class LaysEggTrait implements IJoinWorldTrait {
    @Override
    public void onJoinWorld(IMobStats<?> stats) {
        GoalSelector selector = stats.getEntity().goalSelector;
        if (stats.getEntity() instanceof ChickenEntity) {
            ((ChickenEntity) stats.getEntity()).eggTime = Integer.MAX_VALUE;
        }

        selector.addGoal(3, new RoostGoal(stats.getEntity(), stats));
        selector.addGoal(4, new LayEggGoal(stats.getEntity(), stats));
    }
}
