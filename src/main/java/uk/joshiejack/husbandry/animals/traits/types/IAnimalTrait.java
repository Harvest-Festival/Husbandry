package uk.joshiejack.husbandry.animals.traits.types;

import net.minecraft.util.IStringSerializable;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;

public interface IAnimalTrait extends IStringSerializable {
    default void initTrait(AnimalStats<?> stats) {}

    enum Type {
        NEW_DAY, BI_HOURLY, DATA, AI, ACTION, DISPLAY
    }
}
