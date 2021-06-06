package uk.joshiejack.husbandry.entity.traits.lifestyle;

import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.AbstractMobTrait;
import uk.joshiejack.husbandry.api.trait.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.ai.HideFromRainGoal;

public class AquaphobicTrait extends AbstractMobTrait implements IJoinWorldTrait {
    public AquaphobicTrait(String name) {
        super(name);
    }

    @Override
    public void onJoinWorld(IMobStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(4, new HideFromRainGoal(stats.getEntity(), stats));
    }
}