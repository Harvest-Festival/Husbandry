package uk.joshiejack.husbandry.api.trait;

import uk.joshiejack.husbandry.api.IMobStats;

public interface IInitTrait extends IMobTrait {
    default void initTrait(IMobStats<?> stats) {}
}