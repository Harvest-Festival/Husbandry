package uk.joshiejack.husbandry.entity.traits.product;

import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.AbstractMobTrait;
import uk.joshiejack.husbandry.api.trait.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.ai.FindProductGoal;

public class FindsProductTrait extends AbstractMobTrait implements IJoinWorldTrait {
    public FindsProductTrait(String name) {
        super(name);
    }

    @Override
    public void onJoinWorld(IMobStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(4, new FindProductGoal(stats.getEntity(), stats));
    }
}
