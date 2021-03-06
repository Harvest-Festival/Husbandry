package uk.joshiejack.husbandry.entity.traits.product;

import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.penguinlib.util.helpers.MathsHelper;

//Mobs with this, have their products reset quicker based on happiness
public class MoreProductChanceTrait extends MoreProductTrait {
    @Override
    protected int recalculateProductsPerDay(IMobStats<?> stats) {
        return MathsHelper.convertRange(0, stats.getMaxHappiness(), stats.getEntity().getRandom().nextInt(2),
                2 + stats.getEntity().getRandom().nextInt(2), stats.getHappiness());
    }

    @Override
    public void onBihourlyTick(IMobStats<?> stats) {
        if ((productsProduced == 1 && (101 - MathsHelper.convertRange(0, stats.getMaxHappiness(), 1, 100, stats.getHappiness())) == 0) ||
                (productsProduced == 2 && (201 - MathsHelper.convertRange(0, stats.getMaxHappiness(), 1, 200, stats.getHappiness())) == 0)) {
            stats.resetProduct();
        }
    }
}