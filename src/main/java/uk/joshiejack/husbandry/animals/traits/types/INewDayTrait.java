package uk.joshiejack.husbandry.animals.traits.types;

import uk.joshiejack.husbandry.animals.stats.AnimalStats;

public interface INewDayTrait extends AnimalTraits {
    void onNewDay(AnimalStats<?> stats);
}
