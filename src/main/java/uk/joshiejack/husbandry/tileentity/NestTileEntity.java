package uk.joshiejack.husbandry.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.network.packet.SetInventorySlotPacket;
import uk.joshiejack.penguinlib.tile.inventory.AbstractInventoryTileEntity;

import javax.annotation.Nonnull;

public class NestTileEntity extends AbstractInventoryTileEntity {
    public static final ModelProperty<ItemStack> ITEM_STACK = new ModelProperty<>();

    public NestTileEntity() {
        super(HusbandryTileEntities.NEST.get(), 1);
    }

    @Override
    public boolean canPlaceItem(int slot, @Nonnull ItemStack stack) {
        return false;
    }

    @Override
    public void setItem(int slot, @Nonnull ItemStack stack) {
        super.setItem(slot, stack);
        assert level != null;
        if (level.isClientSide) {
            requestModelDataUpdate();
            level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    @Nonnull
    public ItemStack removeItem(int slot, int amount) {
        ItemStack ret = super.removeItem(slot, amount);
        setChanged();
        return ret;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        assert level != null;
        if (!level.isClientSide)
            PenguinNetwork.sendToNearby(new SetInventorySlotPacket(worldPosition, 0, items.get(0)), this);
    }

    @Override
    @Nonnull
    public IModelData getModelData() {
        return new ModelDataMap.Builder().withInitial(ITEM_STACK, items.get(0)).build();
    }
}
