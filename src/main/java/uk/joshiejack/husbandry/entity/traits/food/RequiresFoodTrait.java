package uk.joshiejack.husbandry.entity.traits.food;

import net.minecraft.util.DamageSource;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.INewDayTrait;
import uk.joshiejack.penguinlib.data.TimeUnitRegistry;

public class RequiresFoodTrait implements INewDayTrait {
    @Override
    public void onNewDay(IMobStats<?> stats) {
        int hunger = stats.getHunger();
        if (hunger >= TimeUnitRegistry.get("require_food_max_days"))
            stats.getEntity().hurt(DamageSource.STARVE, hunger);
    }
}
