package uk.joshiejack.husbandry.entity.traits.product;

import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.ai.FindProductGoal;

public class FindsProductTrait implements IJoinWorldTrait {
    @Override
    public void onJoinWorld(IMobStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(4, new FindProductGoal(stats.getEntity(), stats));
    }
}
