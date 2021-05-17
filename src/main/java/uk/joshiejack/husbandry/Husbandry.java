package uk.joshiejack.husbandry;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.husbandry.crafting.HusbandryRecipes;
import uk.joshiejack.husbandry.data.*;
import uk.joshiejack.husbandry.entity.HusbandryEntities;
import uk.joshiejack.husbandry.inventory.AnimalTrackerContainer;
import uk.joshiejack.husbandry.item.HusbandryItems;
import uk.joshiejack.husbandry.tileentity.HusbandryTileEntities;
import uk.joshiejack.penguinlib.events.CollectRegistryEvent;
import uk.joshiejack.penguinlib.inventory.AbstractBookContainer;
import uk.joshiejack.penguinlib.util.helpers.generic.ReflectionHelper;

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
        Husbandry.CONTAINERS.register(eventBus);
        HusbandryBlocks.BLOCKS.register(eventBus);
        HusbandryItems.ITEMS.register(eventBus);
        HusbandryEntities.ENTITIES.register(eventBus);
        HusbandryEntities.ITEMS.register(eventBus);
        HusbandryRecipes.SERIALIZERS.register(eventBus);
        HusbandrySounds.SOUNDS.register(eventBus);
        HusbandryTileEntities.TILE_ENTITIES.register(eventBus);
    }

    @SubscribeEvent
    public static void onCollectForRegistration(CollectRegistryEvent.Loader event) {
        event.add(AnimalTrait.class, (data, string) -> AnimalTrait.TRAITS.put(string, ReflectionHelper.newInstance(ReflectionHelper.getConstructor(data, String.class), string)));
    }

    @SubscribeEvent
    public static void onDataGathering(final GatherDataEvent event) {
        final DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new HusbandryLootTables(generator));
            BlockTagsProvider blockTags = new HusbandryBlockTags(generator, event.getExistingFileHelper());
            generator.addProvider(blockTags);
            generator.addProvider(new HusbandryItemTags(generator, blockTags, event.getExistingFileHelper()));
            generator.addProvider(new uk.joshiejack.husbandry.data.HusbandryRecipes(generator));
            generator.addProvider(new HusbandryDatabase(generator));
        }

        if (event.includeClient()) {
            generator.addProvider(new HusbandryLanguage(generator));
            generator.addProvider(new HusbandryItemModels(generator, event.getExistingFileHelper()));
        }
    }

//    @SuppressWarnings("ConstantConditions")
//    public static final CreativeTabs TAB = new PenguinTab(MODID, () ->  HusbandryItems.TOOL.getStackFromEnum(ItemTool.Tool.MILKER));
//
//    @Mod.Instance(MODID)
//    public static Husbandry instance;
//    public static Logger logger;
//
//    @SidedProxy
//    public static ServerProxy proxy;
//
//    public static class ServerProxy { public void preInit() {} public void postInit() {}}
//    @SideOnly(Dist.CLIENT)
//    public static class ClientProxy extends ServerProxy {
//        @Override
//        public void preInit() {
//            RenderingRegistry.registerEntityRenderingHandler(EntityDuck.class, RenderDuck::new);
//        }
//
//        @Override
//        public void postInit() {
//            Page.REGISTRY.put("animal_stats", PageStats.INSTANCE);
//        }
//    }
//
//    @Mod.EventHandler
//    public void preInit(FMLPreInitializationEvent event) {
//        logger = event.getModLog();
//        CapabilityStatsHandler.register();
//        NetworkRegistry.INSTANCE.registerGuiHandler(instance, instance);
//    }
//
//    @Mod.EventHandler
//    public void init(FMLInitializationEvent event) {
//        HusbandryItems.init();
//        if (HusbandryConfig.enableTreatTrades) {
//            VillagerRegistry.VillagerProfession farmer = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:farmer"));
//            if (farmer != null) { //2 = shepherd
//                farmer.getCareer(2).addTrade(1, new HusbandryTrades.Generic()); //Tier 1 generic
//                farmer.getCareer(2).addTrade(2, new HusbandryTrades.Special()); //Tier 2 special
//            }
//        }
//    }
//
//    @Mod.EventHandler
//    public void postInit(FMLPostInitializationEvent event) {
//        proxy.postInit();
//    }
//
//    @Nullable
//    @Override
//    public Object getServerGuiElement(int ID, PlayerEntity player, World world, int x, int y, int z) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public Object getClientGuiElement(int ID, PlayerEntity player, World world, int x, int y, int z) {
//        return GuiTracker.INSTANCE;
//    }
}
