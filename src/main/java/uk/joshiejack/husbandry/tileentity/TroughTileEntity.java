package uk.joshiejack.husbandry.tileentity;

import net.minecraft.item.ItemStack;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.husbandry.block.TroughBlock;
import uk.joshiejack.husbandry.entity.traits.food.EatsGrassTrait;
import uk.joshiejack.husbandry.entity.traits.food.EatsSlopTrait;

import javax.annotation.Nonnull;

public class TroughTileEntity extends AbstractFoodSupplyTileEntity {
    public TroughTileEntity() {
        super(HusbandryTileEntities.TROUGH.get());
    }

    @Override
    public int getMaxStackSize() {
        return 4;
    }

    @Override
    public boolean canPlaceItem(int slot, @Nonnull ItemStack stack) {
        return EatsGrassTrait.HAY.contains(stack.getItem()) || EatsSlopTrait.SLOP.contains(stack.getItem());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void setItem(int slot, @Nonnull ItemStack stack) {
        super.setItem(slot, stack);
        if (stack.isEmpty())
            level.setBlock(worldPosition, HusbandryBlocks.TROUGH.get().defaultBlockState().setValue(TroughBlock.FILL, 0), 2);
        else level.setBlock(worldPosition, HusbandryBlocks.TROUGH.get().defaultBlockState()
                .setValue(TroughBlock.TYPE, EatsGrassTrait.HAY.contains(stack.getItem()) ? TroughBlock.FoodType.HAY : TroughBlock.FoodType.SLOP)
                .setValue(TroughBlock.FILL, stack.getCount()), 2);
    }
}