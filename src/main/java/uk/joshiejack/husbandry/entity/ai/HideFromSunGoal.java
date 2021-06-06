package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.entity.MobEntity;
import uk.joshiejack.husbandry.api.IMobStats;

public class HideFromSunGoal extends AbstractHideInsideGoal {
    public HideFromSunGoal(MobEntity entity, IMobStats<?> stats) {
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
