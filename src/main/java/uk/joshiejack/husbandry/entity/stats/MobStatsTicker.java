package uk.joshiejack.husbandry.entity.stats;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.trait.IBiHourlyTrait;
import uk.joshiejack.husbandry.entity.traits.TraitType;
import uk.joshiejack.penguinlib.events.NewDayEvent;

import java.util.List;
import java.util.function.Consumer;

import static uk.joshiejack.husbandry.entity.stats.CapabilityStatsHandler.MOB_STATS_CAPABILITY;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Husbandry.MODID)
public class MobStatsTicker {
    private static final Multimap<RegistryKey<World>, MobStats<?>> stats = HashMultimap.create();

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        if (event.getWorld() instanceof World && !event.getWorld().isClientSide())
            stats.get(((World)event.getWorld()).dimension()).clear();
    }

    @SubscribeEvent
    public static void onNewDay(NewDayEvent event) {
        try {
            Lists.newArrayList(stats.get(event.getWorld().dimension())).stream()
                    .filter(s -> s.entity.isAlive())
                    .forEach(MobStats::onNewDay);
        } catch (Exception ex) { Husbandry.LOGGER.warn("Husbandry encountered an issue when ticking animals for the day... Try not to worry.");}
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.world.isClientSide) return;
        if (event.world.getGameTime() % 2000 == 0) //Every 2 hours
            try {
                Lists.newArrayList(stats.get(event.world.dimension())).stream()
                        .filter(s -> s.entity.isAlive())
                        .forEach(stats -> {
                            List<IBiHourlyTrait> traits = stats.getTraits(TraitType.BI_HOURLY);
                            traits.forEach(trait -> trait.onBihourlyTick(stats));
                        });
            } catch (Exception ex) { Husbandry.LOGGER.warn("Husbandry encountered an issue when ticking animals every two hours... No reason to be concerned!"); }
    }

    private static void run(Entity entity, Consumer<MobStats<?>> consumer) {
        if (entity.level.isClientSide) return;
        LazyOptional<MobStats<?>> stats = entity.getCapability(MOB_STATS_CAPABILITY);
        if (!stats.isPresent() || !stats.resolve().isPresent()) return;
        consumer.accept(stats.resolve().get());
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        run(event.getEntity(), (ms) -> stats.get(event.getWorld().dimension()).add(ms));
    }

    @SubscribeEvent
    public static void onEntityLeaveWorld(EntityLeaveWorldEvent event) {
        run(event.getEntity(), (ms) -> stats.get(event.getWorld().dimension()).remove(ms));
    }
}