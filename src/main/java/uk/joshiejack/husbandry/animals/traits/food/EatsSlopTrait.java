package uk.joshiejack.husbandry.animals.traits.food;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.entity.ai.AbstractMoveToBlockGoal;
import uk.joshiejack.husbandry.entity.ai.EatFoodGoal;

public class EatsSlopTrait extends AbstractFoodTrait {
    public static final ITag.INamedTag<Item> SLOP = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "slop"));

    public EatsSlopTrait(String name) {
        super(name);
    }

    @Override
    protected ITag.INamedTag<Item> getFoodTag() {
        return SLOP;
    }

    @Override
    public void modifyGoals(AnimalStats<?> stats, GoalSelector selector) {
        selector.addGoal(3, new EatFoodGoal(stats.getEntity(), stats, getFoodTag(), AbstractMoveToBlockGoal.Orientation.BESIDE, 16));
    }
}
