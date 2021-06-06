package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.entity.MobEntity;
import uk.joshiejack.husbandry.entity.stats.MobStats;

public class HideFromRainGoal extends AbstractHideInsideGoal {
    public HideFromRainGoal(MobEntity entity, MobStats<?> stats) {
        super(entity, stats);
    }

    @Override
    protected boolean shouldHide() {
        return entity.level.isRainingAt(entity.blockPosition());
    }

    @Override
    protected boolean canStopHiding() {
        return !entity.level.isRaining();
    }
}
