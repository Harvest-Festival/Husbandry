package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.entity.MobEntity;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;

public class HideFromRainGoal extends AbstractHideInsideGoal {
    public HideFromRainGoal(MobEntity entity, AnimalStats<?> stats) {
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
