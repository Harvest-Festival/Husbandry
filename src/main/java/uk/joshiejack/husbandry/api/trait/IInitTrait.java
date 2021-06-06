package uk.joshiejack.husbandry.api.trait;

import uk.joshiejack.husbandry.api.IMobStats;

public interface IInitTrait {
    default void initTrait(IMobStats<?> stats) {}
}