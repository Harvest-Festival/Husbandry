package uk.joshiejack.husbandry.entity.traits.lifestyle;

import uk.joshiejack.husbandry.entity.ai.HideFromStormGoal;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.AbstractMobTrait;
import uk.joshiejack.husbandry.entity.traits.types.IJoinWorldTrait;

public class AstraphobicTrait extends AbstractMobTrait implements IJoinWorldTrait {
    public AstraphobicTrait(String name) {
        super(name);
    }

    @Override
    public void onJoinWorld(MobStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(4, new HideFromStormGoal(stats.getEntity(), stats));
    }
}