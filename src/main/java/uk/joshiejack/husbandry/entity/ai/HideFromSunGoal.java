package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.entity.MobEntity;
import uk.joshiejack.husbandry.entity.stats.MobStats;

public class HideFromSunGoal extends AbstractHideInsideGoal {
    public HideFromSunGoal(MobEntity entity, MobStats<?> stats) {
        super(entity, stats);
    }

    @Override
    protected boolean shouldHide() {
        return entity.level.isDay();
    }

    @Override
    protected boolean canStopHiding() {
        return !entity.level.isDay();
    }
}
