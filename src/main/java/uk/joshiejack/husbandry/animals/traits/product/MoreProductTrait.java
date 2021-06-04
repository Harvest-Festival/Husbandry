package uk.joshiejack.husbandry.animals.traits.product;

import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.types.IBiHourlyTrait;
import uk.joshiejack.husbandry.animals.traits.types.INewDayTrait;
import uk.joshiejack.penguinlib.util.helpers.generic.MathsHelper;

//Animals with this, have their products reset quicker based on happiness
public class MoreProductTrait extends AbstractAnimalTraitProduct implements INewDayTrait, IBiHourlyTrait {
    private int productsPerDay = 1; //How many products the animals give every 24 hours

    public MoreProductTrait(String name) {
        super(name);
    }

    protected int recalculateProductsPerDay(AnimalStats<?> stats) {
        return MathsHelper.convertRange(0, stats.getMaxRelationship(), 1, 5, stats.getHappiness());
    }

    @Override
    public void initTrait(AnimalStats<?> stats) {
        productsPerDay = recalculateProductsPerDay(stats);
    }

    @Override
    public void onBihourlyTick(AnimalStats<?> stats) {
        if (productsProduced < productsPerDay) {
            stats.resetProduct(); //Reset the product every two hours
        }
    }

    @Override
    public void onNewDay(AnimalStats<?> stats) {
        productReset++;
        if (productReset >= stats.getSpecies().getProducts().getDaysBetweenProducts()) {
            stats.resetProduct();
            productReset = 0;
        }

        //Recalculate products per day
        productsPerDay = recalculateProductsPerDay(stats);
    }
}
