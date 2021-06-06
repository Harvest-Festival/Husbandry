package uk.joshiejack.husbandry.api.trait;

import uk.joshiejack.husbandry.api.IMobStats;

public interface IBiHourlyTrait extends IMobTrait {
    void onBihourlyTick(IMobStats<?> stats);
}