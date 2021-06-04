package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.entity.CreatureEntity;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;

public class HideFromStormGoal extends AbstractHideInsideGoal {
    public HideFromStormGoal(CreatureEntity entity, AnimalStats<?> stats) {
        super(entity, stats);
    }

    @Override
    protected boolean shouldHide() {
        return entity.level.isThundering() && entity.level.isRainingAt(entity.blockPosition());
    }

    @Override
    protected boolean canStopHiding() {
        return !entity.level.isThundering();
    }
}
