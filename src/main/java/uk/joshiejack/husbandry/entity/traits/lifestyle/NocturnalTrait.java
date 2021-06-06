package uk.joshiejack.husbandry.entity.traits.lifestyle;

import uk.joshiejack.husbandry.entity.ai.HideFromSunGoal;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.AbstractMobTrait;
import uk.joshiejack.husbandry.entity.traits.types.IJoinWorldTrait;

public class NocturnalTrait extends AbstractMobTrait implements IJoinWorldTrait {
    public NocturnalTrait(String name) {
        super(name);
    }

    @Override
    public void onJoinWorld(MobStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(4, new HideFromSunGoal(stats.getEntity(), stats));
    }
}