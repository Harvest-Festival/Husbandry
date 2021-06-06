package uk.joshiejack.husbandry.api.trait;

import uk.joshiejack.husbandry.api.IMobStats;

public interface IJoinWorldTrait extends IMobTrait {
    void onJoinWorld(IMobStats<?> stats);
}