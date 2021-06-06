package uk.joshiejack.husbandry.entity.traits.lifestyle;

import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.AbstractMobTrait;
import uk.joshiejack.husbandry.api.trait.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.ai.HideFromSunGoal;

public class NocturnalTrait extends AbstractMobTrait implements IJoinWorldTrait {
    public NocturnalTrait(String name) {
        super(name);
    }

    @Override
    public void onJoinWorld(IMobStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(4, new HideFromSunGoal(stats.getEntity(), stats));
    }
}