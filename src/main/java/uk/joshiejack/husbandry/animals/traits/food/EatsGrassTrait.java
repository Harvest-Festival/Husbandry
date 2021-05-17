package uk.joshiejack.husbandry.animals.traits.food;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.SheepEntity;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IGoalTrait;
import uk.joshiejack.husbandry.entity.ai.EatTallGrassGoal;
import uk.joshiejack.penguinlib.util.PenguinLoader;

@PenguinLoader("eats_grass")
public class EatsGrassTrait extends AnimalTrait implements IGoalTrait {
    public EatsGrassTrait(String name) {
        super(name);
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public Goal getGoal(AgeableEntity ageable, AnimalStats<?> stats) {
        if (ageable instanceof SheepEntity) {
            SheepEntity sheep = (SheepEntity) ageable;
            //TODO sheep.goalSelector.removeGoal(sheep.entityAIEatGrass);
        }

        return new EatTallGrassGoal(ageable, stats);
    }
}