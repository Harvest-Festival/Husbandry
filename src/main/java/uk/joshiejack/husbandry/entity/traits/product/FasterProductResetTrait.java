package uk.joshiejack.husbandry.entity.traits.product;

import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.INewDayTrait;
import uk.joshiejack.penguinlib.util.helpers.MathsHelper;

//Mobs with this, have their products reset quicker based on happiness
public class FasterProductResetTrait extends AbstractMobProductTrait implements INewDayTrait {
    @Override
    public void onNewDay(IMobStats<?> stats) {
        productReset++;
        int resetTarget = (1 - MathsHelper.convertRange(0, stats.getMaxHappiness(), 0, 0.4, stats.getHappiness())) * stats.getSpecies().getProducts().getDaysBetweenProducts();
        if (productReset >= resetTarget) {
            stats.resetProduct();
            productReset = 0;
        }
    }
}
