package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.tileentity.NestTileEntity;

import javax.annotation.Nonnull;

import static uk.joshiejack.husbandry.entity.ai.AbstractMoveToBlockGoal.Orientation.ABOVE;

public class RoostGoal extends AbstractMoveToBlockGoal {
    public RoostGoal(MobEntity entity, AnimalStats<?> stats) {
        super(entity, stats, ABOVE, 16);
    }

    @Override
    public boolean canUse() {
        return !entity.level.isDay() && super.canUse();
    }

    @Override
    protected boolean isValidTarget(@Nonnull IWorldReader world, @Nonnull BlockPos pos) {
        return world.getBlockEntity(pos) instanceof NestTileEntity &&
                world.getEntityCollisions(entity, entity.getBoundingBox(), (e) -> true).count() == 0;
    }

    @Override
    public void tick() {
        super.tick();
        if (isNearDestination()) {
            if (entity.level.isDay()) tryTicks = 9999;
            entity.setPos(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D);
        } else entity.getLookControl().setLookAt((double) blockPos.getX() + 0.5D, blockPos.getY(),
                (double) blockPos.getZ() + 0.5D, 10.0F, (float) entity.getMaxHeadXRot());
    }
}
