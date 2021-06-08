package uk.joshiejack.husbandry.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.network.packet.SetInventorySlotPacket;
import uk.joshiejack.penguinlib.tile.inventory.AbstractInventoryTileEntity;

import javax.annotation.Nonnull;

public abstract class AbstractFoodSupplyTileEntity extends AbstractInventoryTileEntity {
    public AbstractFoodSupplyTileEntity(TileEntityType<?> type) {
        super(type, 1);
    }

    @Override
    public int getMaxStackSize() {
        return 2;
    }

    @Nonnull
    public ItemStack removeItem(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    public void consume() {
        ItemStack copied = items.get(0).copy();
        copied.shrink(1);
        setItem(0, copied);
        PenguinNetwork.sendToNearby(new SetInventorySlotPacket(worldPosition, 0, items.get(0)), this);
    }
}
