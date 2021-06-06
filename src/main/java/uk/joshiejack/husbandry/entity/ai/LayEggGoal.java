package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.tileentity.NestTileEntity;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.network.packet.SetInventorySlotPacket;

import javax.annotation.Nonnull;

import static uk.joshiejack.husbandry.entity.ai.AbstractMoveToBlockGoal.Orientation.ABOVE;

public class LayEggGoal extends AbstractMoveToBlockGoal {
    private int eggTimer;

    public LayEggGoal(MobEntity entity, IMobStats<?> stats) {
        super(entity, stats, ABOVE, 8);
    }

    @Override
    public boolean canUse() {
        return stats.canProduceProduct() && entity.getRandom().nextInt(5) == 0 && super.canUse();
    }

    @Override
    protected boolean isValidTarget(@Nonnull IWorldReader world, @Nonnull BlockPos pos) {
        TileEntity tile = world.getBlockEntity(pos);
        return tile instanceof NestTileEntity && ((NestTileEntity)tile).getItem(0).isEmpty();
    }

    @Override
    public void tick() {
        super.tick();
        entity.getLookControl().setLookAt((double) blockPos.getX() + 0.5D, blockPos.getY() + 1,
                (double) blockPos.getZ() + 0.5D, 10.0F, (float) entity.getMaxHeadXRot());

        if (isNearDestination()) {
            eggTimer++;

            entity.setPos(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D);
            if (eggTimer > 60) {
                TileEntity tile = entity.level.getBlockEntity(blockPos);
                if (tile instanceof NestTileEntity) {
                    NestTileEntity nest = ((NestTileEntity) tile);
                    if (nest.getItem(0).isEmpty()) {
                        ItemStack product = stats.getProduct(null).get(0);
                        CompoundNBT data = new CompoundNBT();
                        data.putInt("HatchTime", stats.getSpecies().getDaysToBirth());
                        data.putInt("HeartLevel", stats.getHearts());
                        product.setTag(data);
                        nest.setItem(0, product);
                        stats.setProduced(1);
                        entity.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.2F + 1.0F);
                        tryTicks = 9999;
                        PenguinNetwork.sendToNearby(new SetInventorySlotPacket(blockPos, 0, product), tile);
                    }
                }

                eggTimer = 0;
            }
        }
    }
}
