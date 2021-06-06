package uk.joshiejack.husbandry.entity.traits.product;

import uk.joshiejack.husbandry.entity.ai.DropsProductGoal;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.AbstractMobTrait;
import uk.joshiejack.husbandry.entity.traits.types.IJoinWorldTrait;

public class DropsProductTrait extends AbstractMobTrait implements IJoinWorldTrait {
    public DropsProductTrait(String name) {
        super(name);
    }

    @Override
    public void onJoinWorld(MobStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(4, new DropsProductGoal(stats.getEntity(), stats));
    }
}
