package uk.joshiejack.husbandry.entity.stats;

import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.joshiejack.husbandry.api.trait.IBiHourlyTrait;
import uk.joshiejack.husbandry.entity.traits.TraitType;
import uk.joshiejack.penguinlib.events.NewDayEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static uk.joshiejack.husbandry.Husbandry.MODID;
import static uk.joshiejack.husbandry.entity.stats.CapabilityStatsHandler.MOB_STATS_CAPABILITY;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
public class MobStatsTicker {
    private static final Set<MobStats<?>> stats = Sets.newHashSet();
    private static final List<Runnable> futures = new ArrayList<>();
    private static boolean iterating;

    @SubscribeEvent
    public static void onNewDay(NewDayEvent event) {
        iterating = true;
        futures.forEach(Runnable::run);
        stats.stream().filter(s -> s.entity.isAlive()).forEach(MobStats::onNewDay);
        iterating = false;
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.world.getGameTime() %2000 == 0) //Every 2 hours
            stats.stream().filter(s -> s.entity.isAlive()).forEach(stats -> {
                List<IBiHourlyTrait> traits = stats.getTraits(TraitType.BI_HOURLY);
                traits.forEach(trait -> trait.onBihourlyTick(stats));
            });
    }

    private static void run(Entity entity, Consumer<MobStats<?>> consumer) {
        if (entity.level.isClientSide) return;
        LazyOptional<MobStats<?>> stats = entity.getCapability(MOB_STATS_CAPABILITY);
        if (!stats.isPresent()) return;
        consumer.accept(stats.resolve().get());
    }


    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (iterating) futures.add(() -> run(event.getEntity(), MobStatsTicker.stats::add));
        else run(event.getEntity(), MobStatsTicker.stats::add);
    }

    @SubscribeEvent
    public static void onEntityLeaveWorld(EntityLeaveWorldEvent event) {
        if (iterating) futures.add(() -> run(event.getEntity(), MobStatsTicker.stats::add));
        else run(event.getEntity(), MobStatsTicker.stats::remove);
    }
}
