package uk.joshiejack.husbandry.entity.traits.product;

import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.ai.DropsProductGoal;

public class DropsProductTrait implements IJoinWorldTrait {
    @Override
    public void onJoinWorld(IMobStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(4, new DropsProductGoal(stats.getEntity(), stats));
    }
}
