package uk.joshiejack.husbandry.entity.traits.types;

import uk.joshiejack.husbandry.entity.stats.MobStats;

public interface IJoinWorldTrait extends IMobTrait {
    void onJoinWorld(MobStats<?> stats);
}
