package uk.joshiejack.husbandry.api.trait;

import uk.joshiejack.husbandry.api.IMobStats;

public interface INewDayTrait {
    void onNewDay(IMobStats<?> stats);
}