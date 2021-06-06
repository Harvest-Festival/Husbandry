package uk.joshiejack.husbandry.entity.traits.product;

import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.penguinlib.util.helpers.generic.MathsHelper;

//Mobs with this, have their products reset quicker based on happiness
public class MoreProductChanceTrait extends MoreProductTrait {
    public MoreProductChanceTrait(String name) {
        super(name);
    }

    @Override
    protected int recalculateProductsPerDay(MobStats<?> stats) {
        return 3;
    }

    @Override
    public void onBihourlyTick(MobStats<?> stats) {
        if ((productsProduced == 1 && (101 - MathsHelper.convertRange(0, stats.getMaxRelationship(), 1, 100, stats.getHappiness())) == 0) ||
                (productsProduced == 2 && (201 - MathsHelper.convertRange(0, stats.getMaxRelationship(), 1, 200, stats.getHappiness())) == 0)) {
            stats.resetProduct();
        }
    }
}
