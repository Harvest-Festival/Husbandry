package uk.joshiejack.husbandry.api.trait;

import uk.joshiejack.husbandry.api.IMobStats;

public interface IBiHourlyTrait {
    void onBihourlyTick(IMobStats<?> stats);
}