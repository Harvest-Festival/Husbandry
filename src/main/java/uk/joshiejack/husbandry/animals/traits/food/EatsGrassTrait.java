package uk.joshiejack.husbandry.animals.traits.food;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IGoalTrait;
import uk.joshiejack.husbandry.entity.ai.EatTallGrassGoal;
import uk.joshiejack.penguinlib.util.PenguinLoader;

import java.util.Objects;

@PenguinLoader("eats_grass")
public class EatsGrassTrait extends AnimalTrait implements IGoalTrait {
    public EatsGrassTrait(String name) {
        super(name);
    }

    @Override
    public void modifyGoals(AnimalStats<?> stats, GoalSelector selector) {
        if (stats.getEntity() instanceof SheepEntity) {
            selector.removeGoal(Objects.requireNonNull(ObfuscationReflectionHelper.getPrivateValue(SheepEntity.class, (SheepEntity) stats.getEntity(), "field_146087_bs")));
        }

        selector.addGoal(2, new EatTallGrassGoal(stats.getEntity(), stats));
    }
}