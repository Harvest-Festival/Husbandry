package uk.joshiejack.husbandry.entity.traits.product;

import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.types.IBiHourlyTrait;
import uk.joshiejack.husbandry.entity.traits.types.INewDayTrait;
import uk.joshiejack.penguinlib.util.helpers.generic.MathsHelper;

//Mobs with this, have their products reset quicker based on happiness
public class MoreProductTrait extends AbstractMobProductTrait implements INewDayTrait, IBiHourlyTrait {
    private int productsPerDay = 1; //How many products the mobs give every 24 hours

    public MoreProductTrait(String name) {
        super(name);
    }

    protected int recalculateProductsPerDay(MobStats<?> stats) {
        return MathsHelper.convertRange(0, stats.getMaxRelationship(), 1, 5, stats.getHappiness());
    }

    @Override
    public void initTrait(MobStats<?> stats) {
        productsPerDay = recalculateProductsPerDay(stats);
    }

    @Override
    public void onBihourlyTick(MobStats<?> stats) {
        if (productsProduced < productsPerDay) {
            stats.resetProduct(); //Reset the product every two hours
        }
    }

    @Override
    public void onNewDay(MobStats<?> stats) {
        productReset++;
        if (productReset >= stats.getSpecies().getProducts().getDaysBetweenProducts()) {
            stats.resetProduct();
            productReset = 0;
        }

        //Recalculate products per day
        productsPerDay = recalculateProductsPerDay(stats);
    }
}
