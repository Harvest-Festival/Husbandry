package uk.joshiejack.husbandry.animals.stats;

import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.joshiejack.penguinlib.events.NewDayEvent;

import java.util.Set;
import java.util.function.Consumer;

import static uk.joshiejack.husbandry.Husbandry.MODID;
import static uk.joshiejack.husbandry.animals.stats.CapabilityStatsHandler.ANIMAL_STATS_CAPABILITY;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
public class AnimalStatsTicker {
    private static final Set<AnimalStats<?>> stats = Sets.newHashSet();

    @SubscribeEvent
    public static void onNewDay(NewDayEvent event) {
        stats.stream().filter(s -> s.entity.isAlive()).forEach(AnimalStats::onNewDay);
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.world.getGameTime() %2000 == 0) //Every 2 hours
            stats.stream().filter(s -> s.entity.isAlive()).forEach(AnimalStats::onBihourlyTick);
    }

//    @SubscribeEvent
//    public static void onEntityDeath(LivingDeathEvent event) {
//        if (!event.getEntity().world.isClientSide) {
//            AnimalStats<?> stats = CapabilityHelper.getCapabilityFromEntity(event.getEntity(), ANIMAL_STATS_CAPABILITY);
//            if (stats != null) {
//                AnimalStatsTicker.stats.remove(stats);
//            }
//        }
//    }
//
    /*TODO tests*/
    private static void run(Entity entity, Consumer<AnimalStats<?>> consumer) {
        if (entity.level.isClientSide) return;
        LazyOptional<AnimalStats<?>> stats = entity.getCapability(ANIMAL_STATS_CAPABILITY);
        if (!stats.isPresent()) return;
        consumer.accept(stats.resolve().get());
    }


    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        run(event.getEntity(), AnimalStatsTicker.stats::add);
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityLeaveWorldEvent event) {
        run(event.getEntity(), AnimalStatsTicker.stats::remove);
    }
//
//    @SubscribeEvent //Remove all the stats when the chunk is unloaded
//    public static void onChunkUnloaded(ChunkEvent.Unload event) {
//        if (!event.getWorld().isClientSide()) {
//            for (ClassInheritanceMultiMap<Entity> map : event.getChunk().getEntityLists()) {
//                map.forEach((entity) -> {
//                    LazyOptional<AnimalStats<?>> stats = entity.getCapability(ANIMAL_STATS_CAPABILITY);
//                    if (stats.isPresent())
//                        AnimalStatsTicker.stats.add(stats.resolve().get());
//                });
//            }
//        }
//    }
}
