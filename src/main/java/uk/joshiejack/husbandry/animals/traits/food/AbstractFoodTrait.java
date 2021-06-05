package uk.joshiejack.husbandry.animals.traits.food;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.Hand;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IGoalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IInteractiveTrait;
import uk.joshiejack.husbandry.entity.ai.EatFoodGoal;

public abstract class AbstractFoodTrait extends AnimalTrait implements IGoalTrait, IInteractiveTrait {
    public AbstractFoodTrait(String name) {
        super(name);
    }

    protected abstract ITag.INamedTag<Item> getFoodTag();

    @Override
    public void modifyGoals(AnimalStats<?> stats, GoalSelector selector) {
        selector.addGoal(3, new EatFoodGoal(stats.getEntity(), stats, getFoodTag()));
    }

    @Override
    public boolean onRightClick(AnimalStats<?> stats, PlayerEntity player, Hand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (!getFoodTag().contains(held.getItem()))
            return false;
        stats.feed();
        held.shrink(1);
        return true;
    }
}