package uk.joshiejack.husbandry.animals.traits.food;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IGoalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IInteractiveTrait;
import uk.joshiejack.husbandry.entity.ai.EatFoodGoal;

public class EatsCatFoodTrait extends AnimalTrait implements IGoalTrait, IInteractiveTrait {
    public static final ITag.INamedTag<Item> CAT_FOOD = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "cat_food"));

    public EatsCatFoodTrait(String name) {
        super(name);
    }

    @Override
    public void modifyGoals(AnimalStats<?> stats, GoalSelector selector) {
        selector.addGoal(3, new EatFoodGoal(stats.getEntity(), stats, CAT_FOOD));
    }

    @Override
    public boolean onRightClick(AnimalStats<?> stats, PlayerEntity player, Hand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (!CAT_FOOD.contains(held.getItem()))
            return false;
        stats.feed();
        held.shrink(1);
        return true;
    }
}
