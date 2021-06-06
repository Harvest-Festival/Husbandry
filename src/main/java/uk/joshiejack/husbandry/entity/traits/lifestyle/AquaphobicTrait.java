package uk.joshiejack.husbandry.entity.traits.lifestyle;

import uk.joshiejack.husbandry.entity.ai.HideFromRainGoal;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.AbstractMobTrait;
import uk.joshiejack.husbandry.entity.traits.types.IJoinWorldTrait;

public class AquaphobicTrait extends AbstractMobTrait implements IJoinWorldTrait {
    public AquaphobicTrait(String name) {
        super(name);
    }

    @Override
    public void onJoinWorld(MobStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(4, new HideFromRainGoal(stats.getEntity(), stats));
    }
}