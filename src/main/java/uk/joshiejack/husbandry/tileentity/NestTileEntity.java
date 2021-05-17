package uk.joshiejack.husbandry.tileentity;

import net.minecraft.item.ItemStack;
import uk.joshiejack.penguinlib.tile.inventory.AbstractInventoryTileEntity;

import javax.annotation.Nonnull;

public class NestTileEntity extends AbstractInventoryTileEntity {
    public NestTileEntity() {
        super(HusbandryTileEntities.NEST.get(), 1);
    }

    @Override
    public boolean canPlaceItem(int slot, @Nonnull ItemStack stack) {
        return false;
    }
}
