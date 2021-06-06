package uk.joshiejack.husbandry.entity.traits.product;

import uk.joshiejack.husbandry.entity.ai.FindProductGoal;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.AbstractMobTrait;
import uk.joshiejack.husbandry.entity.traits.types.IJoinWorldTrait;

public class FindsProductTrait extends AbstractMobTrait implements IJoinWorldTrait {
    public FindsProductTrait(String name) {
        super(name);
    }

    @Override
    public void onJoinWorld(MobStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(4, new FindProductGoal(stats.getEntity(), stats));
    }
}
