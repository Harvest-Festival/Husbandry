package uk.joshiejack.husbandry.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import uk.joshiejack.husbandry.crafting.HusbandryRegistries;
import uk.joshiejack.husbandry.crafting.IncubatorRecipe;
import uk.joshiejack.penguinlib.data.TimeUnitRegistry;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.network.packet.SetInventorySlotPacket;
import uk.joshiejack.penguinlib.tile.machine.AbstractIRecipeMachine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("ConstantConditions")
public class IncubatorTileEntity extends AbstractIRecipeMachine<IncubatorRecipe> {
    public static final ModelProperty<ItemStack> ITEM_STACK = new ModelProperty<>();

    public IncubatorTileEntity() {
        super(HusbandryTileEntities.INCUBATOR.get(), HusbandryRegistries.INCUBATOR);
    }

    @Override
    public long getOperationalTime() {
        ItemStack egg = items.get(0);
        if (!egg.hasTag() || !egg.getTag().contains("HatchTime")) return super.getOperationalTime();
        else return TimeUnitRegistry.get("day") * egg.getTag().getInt("HatchTime");
    }

    @Nullable
    private EntityType<?> getEntity(ItemStack stack) {
        IncubatorRecipe recipe = this.getRecipeResult(stack);
        return recipe == null ? null : recipe.getEntity();
    }

    @Override
    public boolean canPlaceItem(int slot, @Nonnull ItemStack stack) {
        return this.items.get(slot).isEmpty() && getEntity(stack) != null;
    }

    @Override
    public void setItem(int slot, @Nonnull ItemStack stack) {
        super.setItem(slot, stack);
        if (level.isClientSide) {
            requestModelDataUpdate();
            level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    @Nonnull
    public ItemStack removeItem(int slot, int amount) {
        return ItemStack.EMPTY;
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

    private boolean isValidSpawnLocation(BlockPos pos) {
        return level.getBlockState(pos).isAir(level, pos) && level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);
    }

    private List<BlockPos> spawnLocations() {
        Set<BlockPos> positions = Sets.newHashSet();
        //Add the surround blocks to start off with
        for (Direction dir : Direction.Plane.HORIZONTAL)
            if (isValidSpawnLocation(worldPosition.relative(dir)))
                positions.add(worldPosition.relative(dir));

        int loop = 6;
        for (int j = 0; j < loop; j++) {
            Set<BlockPos> temp = new HashSet<>(positions);
            for (BlockPos coord : temp) {
                for (Direction theFacing : Direction.values()) {
                    BlockPos offset = coord.relative(theFacing);
                    if (isValidSpawnLocation(offset)) {
                        positions.add(offset);
                    }
                }
            }
        }

        return Lists.newArrayList(positions);
    }

    @Override
    public void finishMachine() {
        List<BlockPos> spawns = spawnLocations();
        if (spawns.size() != 0) {
            getRecipeResult(items.get(0)).hatch((ServerWorld) level, spawns.get(level.random.nextInt(spawns.size())), items.get(0));
            this.items.set(0, ItemStack.EMPTY);
            PenguinNetwork.sendToNearby(new SetInventorySlotPacket(this.worldPosition, 0, ItemStack.EMPTY), this);
            this.setChanged();
        }
    }
}
