package uk.joshiejack.husbandry.plugins.kubejs;

import dev.latvian.kubejs.entity.EntityJS;
import net.minecraft.entity.MobEntity;
import uk.joshiejack.husbandry.api.HusbandryAPI;
import uk.joshiejack.husbandry.api.IMobStats;

import javax.annotation.Nullable;

public class HusbandryUtils {
    @Nullable
    public static IMobStatsJS getMobStats(EntityJS entity) {
        if (!(entity.minecraftEntity instanceof MobEntity)) return null;
        IMobStats<?> stats = HusbandryAPI.instance.getStatsForEntity((MobEntity) entity.minecraftEntity);
        return stats == null ? null : new IMobStatsJS(stats);
    }
}