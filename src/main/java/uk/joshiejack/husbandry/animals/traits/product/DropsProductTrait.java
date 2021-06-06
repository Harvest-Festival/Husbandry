package uk.joshiejack.husbandry.animals.traits.product;

import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AbstractAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.ai.DropsProductGoal;

public class DropsProductTrait extends AbstractAnimalTrait implements IJoinWorldTrait {
    public DropsProductTrait(String name) {
        super(name);
    }

    @Override
    public void onJoinWorld(AnimalStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(4, new DropsProductGoal(stats.getEntity(), stats));
    }
}
