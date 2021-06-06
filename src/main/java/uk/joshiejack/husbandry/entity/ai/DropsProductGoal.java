package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.block.BlockState;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import uk.joshiejack.husbandry.api.IMobStats;

import javax.annotation.Nonnull;

public class DropsProductGoal extends AbstractMoveToBlockGoal {
    public DropsProductGoal(MobEntity entity, IMobStats<?> stats) {
        super(entity, stats, Orientation.BESIDE, 16);
    }

    @Override
    public boolean canUse() {
        return stats.canProduceProduct() && entity.getRandom().nextInt(5) == 0 && super.canUse();
    }

    @Override
    protected boolean isValidTarget(@Nonnull IWorldReader world, @Nonnull BlockPos pos) {
        BlockState below = world.getBlockState(pos.below());
        return below.isFaceSturdy(world, pos.below(), Direction.UP);
    }

    @Override
    public void tick() {
        super.tick();
        entity.getLookControl().setLookAt((double) blockPos.getX() + 0.5D, blockPos.getY() + 1,
                (double) blockPos.getZ() + 0.5D, 10.0F, (float) entity.getMaxHeadXRot());

        if (isNearDestination()) {
            for (ItemStack stack: stats.getSpecies().getProducts().getProduct(entity, null))
                entity.spawnAtLocation(stack);
            stats.setProduced(1);
            tryTicks = 9999;
        }
    }
}
