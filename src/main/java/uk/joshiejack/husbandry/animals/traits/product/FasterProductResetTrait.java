package uk.joshiejack.husbandry.animals.traits.product;

import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.types.INewDayTrait;
import uk.joshiejack.penguinlib.util.helpers.generic.MathsHelper;

//Animals with this, have their products reset quicker based on happiness
public class FasterProductResetTrait extends AbstractAnimalTraitProduct implements INewDayTrait {

    public FasterProductResetTrait(String name) {
        super(name);
    }

    @Override
    public void onNewDay(AnimalStats<?> stats) {
        productReset++;
        int resetTarget = (1 - MathsHelper.convertRange(0, stats.getMaxRelationship(), 0, 0.4, stats.getHappiness())) * stats.getSpecies().getProducts().getDaysBetweenProducts();
        if (productReset >= resetTarget) {
            stats.resetProduct();
            productReset = 0;
        }
    }
}
