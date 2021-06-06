package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.entity.MobEntity;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;

public class ShelterAtNightGoal extends AbstractHideInsideGoal {
    public ShelterAtNightGoal(MobEntity entity, AnimalStats<?> stats) {
        super(entity, stats);
    }

    @Override
    protected boolean shouldHide() {
        return !entity.level.isDay();
    }

    @Override
    protected boolean canStopHiding() {
        return entity.level.isDay();
    }
}
