package uk.joshiejack.husbandry.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import uk.joshiejack.husbandry.crafting.HusbandryRecipes;
import uk.joshiejack.husbandry.crafting.IncubatorRecipe;
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
    public IncubatorTileEntity() {
        super(HusbandryTileEntities.INCUBATOR.get(), HusbandryRecipes.INCUBATOR);
    }

//    @Override
//    public void startMachine(PlayerEntity player) {
//        ItemStack egg = handler.getStackInSlot(0);
//        int days = 7;
//        if (egg.hasTag() && Objects.requireNonNull(egg.getTagCompound()).hasKey("HatchTime")) {
//            days = egg.getTag().getInt("HatchTime");
//        }
//
//        operationTime = TimeHelper.TICKS_PER_DAY * days; //time_unit > day
//        super.startMachine(player);
//    }

    @Nullable
    private EntityType<?> getEntity(ItemStack stack) {
        IncubatorRecipe recipe = this.getRecipeResult(stack);
        return recipe == null ? null : recipe.getEntity();
    }

    @Override
    public boolean canPlaceItem(int slot, @Nonnull ItemStack stack) {
        return this.items.get(slot).isEmpty() && getEntity(stack) != null;
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
            getRecipeResult(items.get(0)).hatch((ServerWorld) level, spawns.get(level.random.nextInt(spawns.size())));
            this.items.set(0, ItemStack.EMPTY);
            PenguinNetwork.sendToNearby(new SetInventorySlotPacket(this.worldPosition, 0, ItemStack.EMPTY), this);
            this.setChanged();
        }
    }
}
