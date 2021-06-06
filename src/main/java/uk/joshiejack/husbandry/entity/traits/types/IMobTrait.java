package uk.joshiejack.husbandry.entity.traits.types;

import net.minecraft.util.IStringSerializable;
import uk.joshiejack.husbandry.entity.stats.MobStats;

public interface IMobTrait extends IStringSerializable {
    default void initTrait(MobStats<?> stats) {}

    enum Type {
        NEW_DAY, BI_HOURLY, DATA, ON_JOIN, ACTION, DISPLAY
    }
}
