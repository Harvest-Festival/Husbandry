package uk.joshiejack.husbandry.animals.traits.product;

import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AbstractAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.ai.FindProductGoal;

public class FindsProductTrait extends AbstractAnimalTrait implements IJoinWorldTrait {
    public FindsProductTrait(String name) {
        super(name);
    }

    @Override
    public void onJoinWorld(AnimalStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(4, new FindProductGoal(stats.getEntity(), stats));
    }
}
