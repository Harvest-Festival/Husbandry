package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.entity.CreatureEntity;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;

public class HideFromSunGoal extends AbstractHideInsideGoal {
    public HideFromSunGoal(CreatureEntity entity, AnimalStats<?> stats) {
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
