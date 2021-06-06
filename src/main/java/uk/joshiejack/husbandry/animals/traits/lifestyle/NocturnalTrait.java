package uk.joshiejack.husbandry.animals.traits.lifestyle;

import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AbstractAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.ai.HideFromSunGoal;

public class NocturnalTrait extends AbstractAnimalTrait implements IJoinWorldTrait {
    public NocturnalTrait(String name) {
        super(name);
    }

    @Override
    public void onJoinWorld(AnimalStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(4, new HideFromSunGoal(stats.getEntity(), stats));
    }
}