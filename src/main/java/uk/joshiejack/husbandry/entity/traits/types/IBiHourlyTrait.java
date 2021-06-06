package uk.joshiejack.husbandry.entity.traits.types;

import uk.joshiejack.husbandry.entity.stats.MobStats;

public interface IBiHourlyTrait extends IMobTrait {
    void onBihourlyTick(MobStats<?> stats);
}
