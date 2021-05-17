package uk.joshiejack.husbandry.animals.traits.types;

import uk.joshiejack.husbandry.animals.stats.AnimalStats;

public interface IBiHourlyTrait extends AnimalTraits {
    void onBihourlyTick(AnimalStats<?> stats);
}
