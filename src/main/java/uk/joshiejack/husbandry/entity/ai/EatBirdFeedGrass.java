package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.tileentity.FeedingTrayTileEntity;

import javax.annotation.Nonnull;

public class EatBirdFeedGrass extends AbstractMoveToBlockGoal {
    public EatBirdFeedGrass(CreatureEntity entity, AnimalStats<?> stats) {
        super(entity, stats, Orientation.ABOVE, 8);
    }

    @Override
    public boolean canUse() {
        return !stats.hasEaten() && entity.getRandom().nextInt(5) == 0 && super.canUse();
    }

    @Override
    protected boolean isValidTarget(@Nonnull IWorldReader world, @Nonnull BlockPos pos) {
        TileEntity tile = world.getBlockEntity(pos);
        if (tile instanceof FeedingTrayTileEntity) {
            return ((FeedingTrayTileEntity)tile).getItem(0).getCount() > 0;
        } else return false;
    }

    @Override
    public void tick() {
        super.tick();
        entity.getLookControl().setLookAt((double) blockPos.getX() + 0.5D, blockPos.getY() + 1,
                (double) blockPos.getZ() + 0.5D, 10.0F, (float) entity.getMaxHeadXRot());
        if (isNearDestination()) {
            TileEntity tile = entity.level.getBlockEntity(blockPos);
            if (tile instanceof FeedingTrayTileEntity) {
                FeedingTrayTileEntity feeder = (FeedingTrayTileEntity) tile;
                if (feeder.getItem(0).getCount() > 0) {
                    feeder.consume();
                    stats.feed();
                    entity.playAmbientSound();
                    tryTicks = 9999;
                }
            }
        }
    }
}
