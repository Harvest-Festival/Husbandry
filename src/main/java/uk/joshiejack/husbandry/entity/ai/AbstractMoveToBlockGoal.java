package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.math.BlockPos;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;

public abstract class AbstractMoveToBlockGoal extends MoveToBlockGoal {
    protected final AnimalStats<?> stats;
    protected final CreatureEntity entity;
    protected final Orientation orientation;
    private boolean reachedTarget;

    public AbstractMoveToBlockGoal(CreatureEntity entity, AnimalStats<?> stats, Orientation orientation, int searchLength) {
        super(entity, 1D, searchLength);
        this.entity = entity;
        this.orientation = orientation;
        this.stats = stats;
    }

    protected boolean isReachedTarget() {
        return this.reachedTarget;
    }

    public boolean isNearDestination() {
        return isReachedTarget();
    }

    private boolean isNearDestination(BlockPos pos) {
        switch (orientation) {
            case ABOVE:
             return entity.blockPosition().closerThan(pos, 1);
            case IN:
                entity.blockPosition().closerThan(pos, 1);
            case BESIDE:
                entity.blockPosition().closerThan(pos, 2.5);
            default:
                return false;
        }
    }

    @Override
    public void tick() {
        if (!isNearDestination(blockPos)) {
            this.reachedTarget = false;
            ++this.tryTicks;

            if (this.tryTicks % 40 == 0) {
                entity.getNavigation().moveTo((double) ((float) blockPos.getX()) + 0.5D,
                        blockPos.getY() + 1, (double) ((float) blockPos.getZ()) + 0.5D, 1D);
            }
        } else {
            this.reachedTarget = true;
            --this.tryTicks;
        }
    }

    public void resetRunTimer() {
        this.nextStartTick = 0;
    }

    public enum Orientation {
        ABOVE, IN, BESIDE
    }
}
