package uk.joshiejack.husbandry.animals.traits.types;

import uk.joshiejack.husbandry.animals.stats.AnimalStats;

public interface IJoinWorldTrait extends IAnimalTrait {
    void onJoinWorld(AnimalStats<?> stats);
}
