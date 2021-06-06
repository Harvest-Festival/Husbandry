package uk.joshiejack.husbandry.entity.traits.types;

import uk.joshiejack.husbandry.entity.stats.MobStats;

public interface INewDayTrait extends IMobTrait {
    void onNewDay(MobStats<?> stats);
}
