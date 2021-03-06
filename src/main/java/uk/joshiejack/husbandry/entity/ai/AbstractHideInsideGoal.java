package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import uk.joshiejack.husbandry.api.IMobStats;

import javax.annotation.Nonnull;
import java.util.Objects;

import static uk.joshiejack.husbandry.entity.ai.AbstractMoveToBlockGoal.Orientation.ABOVE;

public abstract class AbstractHideInsideGoal extends AbstractMoveToBlockGoal {
    public AbstractHideInsideGoal(MobEntity entity, IMobStats<?> stats) {
        super(entity, stats, ABOVE, 8);
    }

    @Override
    public boolean canUse() {
        return shouldHide() && entity.getRandom().nextInt(5) == 0 && super.canUse();
    }

    @Override
    protected boolean findNearestBlock() {
        if (shouldHide()) return super.findNearestBlock();
        blockPos = entity instanceof CreatureEntity ? new BlockPos(Objects.requireNonNull(RandomPositionGenerator.getLandPos((CreatureEntity) entity, 10, 7))) : blockPos;
        return isValidTarget(entity.level, blockPos);
    }

    protected abstract boolean shouldHide();

    @Override
    protected boolean isValidTarget(@Nonnull IWorldReader world, @Nonnull BlockPos pos) {
        return world.getBlockState(pos.above()).getCollisionShape(world, pos.above()).isEmpty()
            && world.getBlockState(pos.above(2)).getCollisionShape(world, pos.above(2)).isEmpty()
                && !world.canSeeSky(pos.above());
    }

    @Override
    public void tick() {
        super.tick();
        if (isNearDestination() && canStopHiding())
            tryTicks = 2000;
    }

    protected abstract boolean canStopHiding();
}
