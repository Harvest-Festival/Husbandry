package uk.joshiejack.husbandry.api.trait;

import uk.joshiejack.husbandry.api.IMobStats;

public interface INewDayTrait extends IMobTrait {
    void onNewDay(IMobStats<?> stats);
}