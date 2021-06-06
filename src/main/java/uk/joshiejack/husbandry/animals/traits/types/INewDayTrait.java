package uk.joshiejack.husbandry.animals.traits.types;

import uk.joshiejack.husbandry.animals.stats.AnimalStats;

public interface INewDayTrait extends IAnimalTrait {
    void onNewDay(AnimalStats<?> stats);
}
