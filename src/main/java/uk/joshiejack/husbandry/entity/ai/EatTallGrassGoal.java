package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.MobEntity;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.event.ForgeEventFactory;
import uk.joshiejack.husbandry.api.IMobStats;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Predicate;

import static uk.joshiejack.husbandry.entity.ai.AbstractMoveToBlockGoal.Orientation.IN;

public class EatTallGrassGoal extends AbstractMoveToBlockGoal {
    private static final Predicate<BlockState> IS_TALL_GRASS = BlockStateMatcher.forBlock(Blocks.GRASS);
    private static final Predicate<BlockState> IS_DOUBLE_TALL_GRASS = BlockStateMatcher.forBlock(Blocks.TALL_GRASS).where(DoublePlantBlock.HALF, half -> Objects.equals(half, DoubleBlockHalf.LOWER));

    public EatTallGrassGoal(MobEntity entity, IMobStats<?> stats) {
        super(entity, stats, IN, 8);
    }

    @Override
    public boolean canUse() {
        return stats.isHungry() && entity.getRandom().nextInt(50) == 0 && super.canUse();
    }

    @Override
    protected boolean isValidTarget(@Nonnull IWorldReader world, @Nonnull BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return IS_TALL_GRASS.test(state) || IS_DOUBLE_TALL_GRASS.test(state); //TODO: Allow for custom grass eating?
    }

    @Override
    public void tick() {
        super.tick();
        entity.getLookControl().setLookAt((double) blockPos.getX() + 0.5D, blockPos.getY(),
                (double) blockPos.getZ() + 0.5D, 10.0F, (float) entity.getMaxHeadXRot());
        if (isNearDestination()) {
            BlockState state = entity.level.getBlockState(blockPos);
            if (IS_TALL_GRASS.test(state)) {
                if (ForgeEventFactory.getMobGriefingEvent(entity.level, entity)) {
                    entity.level.destroyBlock(blockPos, false);
                }

                stats.feed();
                tryTicks = 9999;
            } else if (IS_DOUBLE_TALL_GRASS.test(state)) {
                if (ForgeEventFactory.getMobGriefingEvent(entity.level, entity)) {
                    entity.level.destroyBlock(blockPos, false);
                    entity.level.setBlock(blockPos, Blocks.GRASS.defaultBlockState(), 2);
                }

                stats.feed();
                tryTicks = 9999;
            }
        }
    }
}
