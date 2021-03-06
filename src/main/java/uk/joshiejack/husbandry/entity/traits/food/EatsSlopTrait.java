package uk.joshiejack.husbandry.entity.traits.food;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.entity.ai.AbstractMoveToBlockGoal;
import uk.joshiejack.husbandry.entity.ai.EatFoodGoal;

public class EatsSlopTrait extends AbstractFoodTrait {
    public static final ITag.INamedTag<Item> SLOP = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "slop"));

    @Override
    protected ITag.INamedTag<Item> getFoodTag() {
        return SLOP;
    }

    @Override
    public void onJoinWorld(IMobStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(3, new EatFoodGoal(stats.getEntity(), stats, getFoodTag(), AbstractMoveToBlockGoal.Orientation.BESIDE, 16));
    }
}
