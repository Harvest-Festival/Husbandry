package uk.joshiejack.husbandry.entity.traits.lifestyle;

import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.ai.HideFromStormGoal;

public class AstraphobicTrait implements IJoinWorldTrait {
    @Override
    public void onJoinWorld(IMobStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(4, new HideFromStormGoal(stats.getEntity(), stats));
    }
}