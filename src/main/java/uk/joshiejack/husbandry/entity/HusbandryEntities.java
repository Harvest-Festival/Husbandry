package uk.joshiejack.husbandry.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.joshiejack.husbandry.Husbandry;


@Mod.EventBusSubscriber(modid = Husbandry.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@SuppressWarnings("ConstantConditions,unchecked,rawtypes")
public class HusbandryEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Husbandry.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Husbandry.MODID);
    public static final RegistryObject<EntityType<?>> DUCK = registerAnimal("duck", DuckEntity::new, 0.4F, 0.7F, 0x096913, 0xEADCDC);
    public static final RegistryObject<EntityType<?>> DUCK_EGG = registerObject("duck_egg", DuckEggEntity::new, 0.25F, 0.25F);
    private static <T extends Entity> RegistryObject<EntityType<?>> registerObject(String name, EntityType.IFactory<T> factory, float width, float height) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(factory, EntityClassification.MISC).sized(width, height).clientTrackingRange(4).build(Husbandry.MODID + ":" + name));
    }

    private static <T extends Entity> RegistryObject<EntityType<?>> registerAnimal(String name, EntityType.IFactory<T> factory, float width, float height, int colorPrimary, int colorSecondary) {
        EntityType<?> type = EntityType.Builder.of(factory, EntityClassification.CREATURE).sized(width, height).clientTrackingRange(10).build(Husbandry.MODID + ":" + name);
        ITEMS.register(name + "_spawn_egg", () -> new SpawnEggItem(type, colorPrimary, colorSecondary, new Item.Properties().tab(Husbandry.TAB)));
        return ENTITIES.register(name, () -> type);
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put((EntityType<? extends LivingEntity>) DUCK.get(), DuckEntity.createAttributes().build());
        EntitySpawnPlacementRegistry.register((EntityType) DUCK.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractFishEntity::checkFishSpawnRules);
    }
}
