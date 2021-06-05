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

public class EatsBirdFeedTrait extends AbstractFoodTrait {
    public static final ITag.INamedTag<Item> BIRD_FEED = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "bird_feed"));

    public EatsBirdFeedTrait(String name) {
        super(name);
    }

    @Override
    protected ITag.INamedTag<Item> getFoodTag() {
        return BIRD_FEED;
    }

    @Override
    public void modifyGoals(AnimalStats<?> stats, GoalSelector selector) {
        selector.addGoal(3, new EatFoodGoal(stats.getEntity(), stats, getFoodTag(), AbstractMoveToBlockGoal.Orientation.ABOVE, 8));
    }
}