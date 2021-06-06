package uk.joshiejack.husbandry.animals.traits.product;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.passive.ChickenEntity;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AbstractAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.ai.LayEggGoal;
import uk.joshiejack.husbandry.entity.ai.RoostGoal;

public class LaysEggTrait extends AbstractAnimalTrait implements IJoinWorldTrait {
    public LaysEggTrait(String name) {
        super(name);
    }

    @Override
    public void onJoinWorld(AnimalStats<?> stats) {
        GoalSelector selector = stats.getEntity().goalSelector;
        if (stats.getEntity() instanceof ChickenEntity) {
            ((ChickenEntity) stats.getEntity()).eggTime = Integer.MAX_VALUE;
        }

        selector.addGoal(3, new RoostGoal(stats.getEntity(), stats));
        selector.addGoal(4, new LayEggGoal(stats.getEntity(), stats));
    }
}
