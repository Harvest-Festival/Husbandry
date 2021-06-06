package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.event.ForgeEventFactory;
import uk.joshiejack.husbandry.entity.stats.MobStats;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

import static uk.joshiejack.husbandry.entity.ai.AbstractMoveToBlockGoal.Orientation.ABOVE;

public class EatGrassBlockGoal extends AbstractMoveToBlockGoal {
    private static final Predicate<BlockState> IS_GRASS = BlockStateMatcher.forBlock(Blocks.GRASS_BLOCK);

    public EatGrassBlockGoal(MobEntity entity, MobStats<?> stats) {
        super(entity, stats, ABOVE, 8);
    }

    @Override
    public boolean canUse() {
        return stats.isHungry() && entity.getRandom().nextInt(50) == 0 && super.canUse();
    }

    @Override
    protected boolean isValidTarget(@Nonnull IWorldReader world, @Nonnull BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return IS_GRASS.test(state); //TODO: Allow for custom grass eating?
    }

    @Override
    public void tick() {
        super.tick();
        entity.getLookControl().setLookAt((double) blockPos.getX() + 0.5D, blockPos.getY(),
                (double) blockPos.getZ() + 0.5D, 10.0F, (float) entity.getMaxHeadXRot());
        if (isNearDestination()) {
            BlockState state = entity.level.getBlockState(blockPos);
            if (IS_GRASS.test(state)) {
                if (ForgeEventFactory.getMobGriefingEvent(entity.level, entity))
                    entity.level.setBlock(blockPos, Blocks.DIRT.defaultBlockState(), 3);
                stats.feed();
                tryTicks = 9999;
            }
        }
    }
}
