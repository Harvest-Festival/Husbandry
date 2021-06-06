package uk.joshiejack.husbandry.api.trait;

import uk.joshiejack.husbandry.api.IMobStats;

public interface IJoinWorldTrait {
    void onJoinWorld(IMobStats<?> stats);
}