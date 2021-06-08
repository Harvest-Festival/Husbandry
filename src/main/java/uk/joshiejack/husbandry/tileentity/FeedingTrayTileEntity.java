package uk.joshiejack.husbandry.tileentity;

import net.minecraft.item.ItemStack;
import uk.joshiejack.husbandry.block.FeedingTrayBlock;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.husbandry.entity.traits.food.EatsBirdFeedTrait;

import javax.annotation.Nonnull;

@SuppressWarnings("ConstantConditions")
public class FeedingTrayTileEntity extends AbstractFoodSupplyTileEntity {
    public FeedingTrayTileEntity() {
        super(HusbandryTileEntities.FEEDING_TRAY.get());
    }

    @Override
    public boolean canPlaceItem(int slot, @Nonnull ItemStack stack) {
        return EatsBirdFeedTrait.BIRD_FEED.contains(stack.getItem());
    }

    @Override
    public void setItem(int slot, @Nonnull ItemStack stack) {
        super.setItem(slot, stack);
        if (stack.isEmpty()) level.setBlock(worldPosition, HusbandryBlocks.FEEDING_TRAY.get().defaultBlockState().setValue(FeedingTrayBlock.FILL, 0), 2);
        else level.setBlock(worldPosition, HusbandryBlocks.FEEDING_TRAY.get().defaultBlockState().setValue(FeedingTrayBlock.FILL, stack.getCount()), 2);
    }
}
