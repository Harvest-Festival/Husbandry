package uk.joshiejack.husbandry.entity.traits.product;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.passive.ChickenEntity;
import uk.joshiejack.husbandry.entity.ai.LayEggGoal;
import uk.joshiejack.husbandry.entity.ai.RoostGoal;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.AbstractMobTrait;
import uk.joshiejack.husbandry.entity.traits.types.IJoinWorldTrait;

public class LaysEggTrait extends AbstractMobTrait implements IJoinWorldTrait {
    public LaysEggTrait(String name) {
        super(name);
    }

    @Override
    public void onJoinWorld(MobStats<?> stats) {
        GoalSelector selector = stats.getEntity().goalSelector;
        if (stats.getEntity() instanceof ChickenEntity) {
            ((ChickenEntity) stats.getEntity()).eggTime = Integer.MAX_VALUE;
        }

        selector.addGoal(3, new RoostGoal(stats.getEntity(), stats));
        selector.addGoal(4, new LayEggGoal(stats.getEntity(), stats));
    }
}
