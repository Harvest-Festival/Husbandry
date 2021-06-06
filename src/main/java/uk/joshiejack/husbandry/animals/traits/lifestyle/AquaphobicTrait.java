package uk.joshiejack.husbandry.animals.traits.lifestyle;

import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AbstractAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.ai.HideFromRainGoal;

public class AquaphobicTrait extends AbstractAnimalTrait implements IJoinWorldTrait {
    public AquaphobicTrait(String name) {
        super(name);
    }

    @Override
    public void onJoinWorld(AnimalStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(4, new HideFromRainGoal(stats.getEntity(), stats));
    }
}