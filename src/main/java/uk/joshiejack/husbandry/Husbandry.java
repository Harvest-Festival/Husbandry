package uk.joshiejack.husbandry;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.food.*;
import uk.joshiejack.husbandry.animals.traits.happiness.CarriableTrait;
import uk.joshiejack.husbandry.animals.traits.happiness.CleanableTrait;
import uk.joshiejack.husbandry.animals.traits.happiness.PettableTrait;
import uk.joshiejack.husbandry.animals.traits.lifestyle.MammalTrait;
import uk.joshiejack.husbandry.animals.traits.lifestyle.PetTrait;
import uk.joshiejack.husbandry.animals.traits.product.*;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.husbandry.crafting.HusbandryRegistries;
import uk.joshiejack.husbandry.data.*;
import uk.joshiejack.husbandry.inventory.AnimalTrackerContainer;
import uk.joshiejack.husbandry.item.HusbandryItems;
import uk.joshiejack.husbandry.tileentity.HusbandryTileEntities;
import uk.joshiejack.penguinlib.inventory.AbstractBookContainer;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@Mod(Husbandry.MODID)
public class Husbandry {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Husbandry.MODID);
    public static final RegistryObject<ContainerType<AbstractBookContainer>> BOOK = CONTAINERS.register("animal_tracker", () -> IForgeContainerType.create((id, inv, data) -> new AnimalTrackerContainer(id)));
    public static final String MODID = "husbandry";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ItemGroup TAB = new ItemGroup(MODID) {
        @Nonnull
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(HusbandryItems.BRUSH.get());
        }
    };

    public Husbandry() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::registerTraits);
        Husbandry.CONTAINERS.register(eventBus);
        HusbandryBlocks.BLOCKS.register(eventBus);
        HusbandryItems.ITEMS.register(eventBus);
        HusbandryRegistries.SERIALIZERS.register(eventBus);
        HusbandrySounds.SOUNDS.register(eventBus);
        HusbandryTileEntities.TILE_ENTITIES.register(eventBus);
    }

    private void registerTraits(FMLCommonSetupEvent event) {
        AnimalTrait.registerTrait("eats_bird_feed",         EatsBirdFeedTrait.class);
        AnimalTrait.registerTrait("eats_cat_food",          EatsCatFoodTrait.class);
        AnimalTrait.registerTrait("eats_dog_food",          EatsDogFoodTrait.class);
        AnimalTrait.registerTrait("eats_grass",             EatsGrassTrait.class);
        AnimalTrait.registerTrait("eats_hay",               EatsHayTrait.class);
        AnimalTrait.registerTrait("eats_rabbit_food",       EatsRabbitFoodTrait.class);
        AnimalTrait.registerTrait("eats_slop",              EatsSlopTrait.class);
        AnimalTrait.registerTrait("carriable",              CarriableTrait.class);
        AnimalTrait.registerTrait("cleanable",              CleanableTrait.class);
        AnimalTrait.registerTrait("pettable",               PettableTrait.class);
        AnimalTrait.registerTrait("mammal",                 MammalTrait.class);
        AnimalTrait.registerTrait("pet",                    PetTrait.class);
        AnimalTrait.registerTrait("drops_product",          DropsProductTrait.class);
        AnimalTrait.registerTrait("faster_product_reset",   FasterProductResetTrait.class);
        AnimalTrait.registerTrait("finds_product",          FindsProductTrait.class);
        AnimalTrait.registerTrait("lays_egg",               LaysEggTrait.class);
        AnimalTrait.registerTrait("milkable",               MilkableTrait.class);
        AnimalTrait.registerTrait("more_product_chance",    MoreProductChanceTrait.class);
        AnimalTrait.registerTrait("more_product",           MoreProductTrait.class);
        AnimalTrait.registerTrait("shearable",              ShearableTrait.class);
    }

    @SubscribeEvent
    public static void onDataGathering(final GatherDataEvent event) {
        final DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new HusbandryLootTables(generator));
            BlockTagsProvider blockTags = new HusbandryBlockTags(generator, event.getExistingFileHelper());
            generator.addProvider(blockTags);
            generator.addProvider(new HusbandryItemTags(generator, blockTags, event.getExistingFileHelper()));
            generator.addProvider(new HusbandryRecipes(generator));
            generator.addProvider(new HusbandryDatabase(generator));
            generator.addProvider(new HusbandryBlockStates(generator, event.getExistingFileHelper()));
        }

        if (event.includeClient()) {
            generator.addProvider(new HusbandryLanguage(generator));
            generator.addProvider(new HusbandryItemModels(generator, event.getExistingFileHelper()));
        }
    }

    public static class HusbandrySounds {
        public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);
        public static final RegistryObject<SoundEvent> BRUSH = createSoundEvent("brush");

        private static RegistryObject<SoundEvent> createSoundEvent(@Nonnull String name) {
            return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(MODID, name)));
        }
    }
}
