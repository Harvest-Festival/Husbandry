package uk.joshiejack.husbandry.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Level;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.trait.IMobTrait;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.stats.Products;
import uk.joshiejack.husbandry.entity.stats.Species;
import uk.joshiejack.husbandry.network.RequestDataPacket;
import uk.joshiejack.penguinlib.events.DatabaseLoadedEvent;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Husbandry.MODID)
public class MobDataLoader {
    public static final Map<String, IMobTrait> TRAITS = Maps.newHashMap();

    @SubscribeEvent
    public static void onDatabaseLoaded(DatabaseLoadedEvent event) {
        Map<String, Species> types = Maps.newHashMap();
        event.table("mob_species").rows().forEach(row -> {
            String name = row.get("name");
            List<IMobTrait> traits = Lists.newArrayList();
            event.table("mob_traits").where("species=" + name)
                    .forEach(trait -> traits.add(TRAITS.get(trait.get("trait").toString())));
            Products products = row.get("products loot table").equals("none") ? Products.NONE :
                    new Products(row.get("product frequency"), row.getRL("products loot table"));
            Species type = new Species(row.get("min age"), row.get("max age"),
                    row.item("treat item"),
                    row.get("generic treats"), row.get("species treats")
                    , row.get("days to birth"), row.get("days to maturity"),
                    products, traits);
            types.put(name, type);
            Husbandry.LOGGER.log(Level.INFO, "Registered a new mob species: " + name);
        });

        event.table("mob_entities").rows().forEach(entities -> {
            String name = entities.get("species");
            EntityType<?> type = entities.entity();
            if (type != null) {
                Species.TYPES.put(type, types.get(name));
                Husbandry.LOGGER.log(Level.INFO, "Registered the entity " + entities.get("entity") + " as a " + name);
            }
        });
    }

    @SubscribeEvent
    public static void onCapabilityLoading(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof MobEntity && Species.TYPES.containsKey(entity.getType()))
            event.addCapability(new ResourceLocation(Husbandry.MODID, "stats"),
                    new MobStats<>((MobEntity) entity, Species.TYPES.get(entity.getType())));
    }

    @SubscribeEvent
    public static void onEntityJoinedWorld(EntityJoinWorldEvent event) {
        //Request data about this mob from the server
        if (event.getWorld().isClientSide)
            PenguinNetwork.sendToServer(new RequestDataPacket(event.getEntity().getId()));
    }
}
