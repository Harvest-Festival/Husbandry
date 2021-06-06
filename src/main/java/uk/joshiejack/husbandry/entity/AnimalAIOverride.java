package uk.joshiejack.husbandry.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.types.IAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.ai.HideFromStormGoal;

import java.util.stream.Stream;

import static uk.joshiejack.husbandry.Husbandry.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
public class AnimalAIOverride {
    @SubscribeEvent
    public static void onEntityJoinedWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (!entity.level.isClientSide && entity instanceof MobEntity) {
            AnimalStats<?> stats = AnimalStats.getStats(entity);
            if (stats != null) {
                MobEntity mob = ((MobEntity) entity);
                //All animals will hide from storms
                mob.goalSelector.addGoal(4, new HideFromStormGoal(mob, stats));
                Stream<IJoinWorldTrait> traits = stats.getTraits(IAnimalTrait.Type.ON_JOIN);
                traits.forEach(trait -> trait.onJoinWorld(stats));
            }
        }
    }
}
