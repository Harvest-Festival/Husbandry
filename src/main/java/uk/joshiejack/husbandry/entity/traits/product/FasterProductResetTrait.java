package uk.joshiejack.husbandry.entity.traits.product;

import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.types.INewDayTrait;
import uk.joshiejack.penguinlib.util.helpers.generic.MathsHelper;

//Mobs with this, have their products reset quicker based on happiness
public class FasterProductResetTrait extends AbstractMobProductTrait implements INewDayTrait {

    public FasterProductResetTrait(String name) {
        super(name);
    }

    @Override
    public void onNewDay(MobStats<?> stats) {
        productReset++;
        int resetTarget = (1 - MathsHelper.convertRange(0, stats.getMaxRelationship(), 0, 0.4, stats.getHappiness())) * stats.getSpecies().getProducts().getDaysBetweenProducts();
        if (productReset >= resetTarget) {
            stats.resetProduct();
            productReset = 0;
        }
    }
}
