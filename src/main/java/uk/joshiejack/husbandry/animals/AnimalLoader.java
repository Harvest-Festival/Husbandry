package uk.joshiejack.husbandry.animals;

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
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AbstractAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IAnimalTrait;
import uk.joshiejack.husbandry.network.RequestDataPacket;
import uk.joshiejack.penguinlib.events.DatabaseLoadedEvent;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Husbandry.MODID)
public class AnimalLoader {
    @SubscribeEvent
    public static void onDatabaseLoaded(DatabaseLoadedEvent event) {
        Map<String, AnimalSpecies> types = Maps.newHashMap();
        event.table("animal_species").rows().forEach(row -> {
            String name = row.get("name");
            List<IAnimalTrait> traits = Lists.newArrayList();
            event.table("animal_traits").where("species=" + name)
                    .forEach(trait -> traits.add(AbstractAnimalTrait.TRAITS.get(trait.get("trait").toString())));
            AnimalProducts products = row.get("products loot table").equals("none") ? AnimalProducts.NONE :
                    new AnimalProducts(row.get("product frequency"), row.getRL("products loot table"));
            AnimalSpecies type = new AnimalSpecies(row.get("min age"), row.get("max age"),
                    row.item("treat item"),
                    row.get("generic treats"), row.get("species treats")
                    , row.get("days to birth"), row.get("days to maturity"),
                    products, traits);
            types.put(name, type);
            Husbandry.LOGGER.log(Level.INFO, "Registered a new animal species: " + name);
        });

        event.table("animal_entities").rows().forEach(entities -> {
            String name = entities.get("species");
            EntityType<?> type = entities.entity();
            if (type != null) {
                AnimalSpecies.TYPES.put(type, types.get(name));
                Husbandry.LOGGER.log(Level.INFO, "Registered the entity " + entities.get("entity") + " as a " + name);
            }
        });
    }

    @SubscribeEvent
    public static void onCapabilityLoading(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof MobEntity && AnimalSpecies.TYPES.containsKey(entity.getType()))
            event.addCapability(new ResourceLocation(Husbandry.MODID, "stats"),
                    new AnimalStats<>((MobEntity) entity, AnimalSpecies.TYPES.get(entity.getType())));
    }

    @SubscribeEvent
    public static void onEntityJoinedWorld(EntityJoinWorldEvent event) {
        if (event.getWorld().isClientSide)//Request data about this animal from the server
            PenguinNetwork.sendToServer(new RequestDataPacket(event.getEntity().getId()));
    }
}
