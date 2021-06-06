package uk.joshiejack.husbandry.animals.traits.food;

import net.minecraft.util.DamageSource;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AbstractAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.INewDayTrait;
import uk.joshiejack.penguinlib.data.TimeUnitRegistry;

public class RequiresFoodTrait extends AbstractAnimalTrait implements INewDayTrait {
    public RequiresFoodTrait(String name) {
        super(name);
    }

    @Override
    public void onNewDay(AnimalStats<?> stats) {
        int hunger = stats.getHunger();
        if (hunger >= TimeUnitRegistry.get("require_food_max_days"))
            stats.getEntity().hurt(DamageSource.STARVE, hunger);
    }
}
