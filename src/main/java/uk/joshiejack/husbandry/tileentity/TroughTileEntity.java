package uk.joshiejack.husbandry.tileentity;

import net.minecraft.item.ItemStack;
import uk.joshiejack.husbandry.animals.traits.food.EatsGrassTrait;
import uk.joshiejack.husbandry.animals.traits.food.EatsSlopTrait;

import javax.annotation.Nonnull;

public class TroughTileEntity extends AbstractFoodSupplyTileEntity {
    public TroughTileEntity() {
        super(HusbandryTileEntities.TROUGH.get());
    }

    @Override
    public boolean canPlaceItem(int slot, @Nonnull ItemStack stack) {
        return EatsGrassTrait.HAY.contains(stack.getItem()) || EatsSlopTrait.SLOP.contains(stack.getItem());
    }
}