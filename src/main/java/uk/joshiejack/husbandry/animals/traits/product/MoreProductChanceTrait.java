package uk.joshiejack.husbandry.animals.traits.product;

import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.penguinlib.util.PenguinLoader;
import uk.joshiejack.penguinlib.util.helpers.generic.MathsHelper;

@PenguinLoader("more_product_chance") //Animals with this, have their products reset quicker based on happiness
public class MoreProductChanceTrait extends MoreProductTrait {
    public MoreProductChanceTrait(String name) {
        super(name);
    }

    @Override
    protected int recalculateProductsPerDay(AnimalStats<?> stats) {
        return 3;
    }

    @Override
    public void onBihourlyTick(AnimalStats<?> stats) {
        if ((productsProduced == 1 && (101 - MathsHelper.convertRange(0, stats.getMaxRelationship(), 1, 100, stats.getHappiness())) == 0) ||
                (productsProduced == 2 && (201 - MathsHelper.convertRange(0, stats.getMaxRelationship(), 1, 200, stats.getHappiness())) == 0)) {
            stats.resetProduct();
        }
    }
}
