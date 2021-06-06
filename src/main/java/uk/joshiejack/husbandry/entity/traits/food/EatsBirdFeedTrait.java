package uk.joshiejack.husbandry.entity.traits.food;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.entity.ai.AbstractMoveToBlockGoal;
import uk.joshiejack.husbandry.entity.ai.EatFoodGoal;

public class EatsBirdFeedTrait extends AbstractFoodTrait {
    public static final ITag.INamedTag<Item> BIRD_FEED = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "bird_feed"));


    @Override
    protected ITag.INamedTag<Item> getFoodTag() {
        return BIRD_FEED;
    }

    @Override
    public void onJoinWorld(IMobStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(3, new EatFoodGoal(stats.getEntity(), stats, getFoodTag(), AbstractMoveToBlockGoal.Orientation.ABOVE, 8));
    }
}