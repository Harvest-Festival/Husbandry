package uk.joshiejack.husbandry.entity.traits.product;

import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IBiHourlyTrait;
import uk.joshiejack.husbandry.api.trait.IInitTrait;
import uk.joshiejack.husbandry.api.trait.INewDayTrait;
import uk.joshiejack.penguinlib.util.helpers.generic.MathsHelper;

//Mobs with this, have their products reset quicker based on happiness
public class MoreProductTrait extends AbstractMobProductTrait implements IInitTrait, INewDayTrait, IBiHourlyTrait {
    private int productsPerDay = 1; //How many products the mobs give every 24 hours

    protected int recalculateProductsPerDay(IMobStats<?> stats) {
        return MathsHelper.convertRange(0, stats.getMaxRelationship(), 1, 5, stats.getHappiness());
    }

    @Override
    public void initTrait(IMobStats<?> stats) {
        productsPerDay = recalculateProductsPerDay(stats);
    }

    @Override
    public void onBihourlyTick(IMobStats<?> stats) {
        if (productsProduced < productsPerDay) {
            stats.resetProduct(); //Reset the product every two hours
        }
    }

    @Override
    public void onNewDay(IMobStats<?> stats) {
        productReset++;
        if (productReset >= stats.getSpecies().getProducts().getDaysBetweenProducts()) {
            stats.resetProduct();
            productReset = 0;
        }

        //Recalculate products per day
        productsPerDay = recalculateProductsPerDay(stats);
    }
}
