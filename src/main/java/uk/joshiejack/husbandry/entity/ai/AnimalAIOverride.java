package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.types.AnimalTraits;
import uk.joshiejack.husbandry.animals.traits.types.IGoalTrait;

import java.util.List;

import static uk.joshiejack.husbandry.Husbandry.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
public class AnimalAIOverride {
    @SubscribeEvent
    public static void onEntityJoinedWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (!entity.level.isClientSide && entity instanceof AgeableEntity) {
            AnimalStats<?> stats = AnimalStats.getStats(entity);
            if (stats != null) {
                AgeableEntity ageable = ((AgeableEntity) entity);
                List<IGoalTrait> traits = stats.getType().getTraits(AnimalTraits.Type.AI);
                traits.forEach(trait -> trait.modifyGoals(stats, ageable.goalSelector));
            }
        }
    }
}
