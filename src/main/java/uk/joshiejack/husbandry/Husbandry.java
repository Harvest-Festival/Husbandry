package uk.joshiejack.husbandry;

import com.google.common.base.CaseFormat;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.MobEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.joshiejack.husbandry.api.HusbandryAPI;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IMobTrait;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.husbandry.crafting.HusbandryRegistries;
import uk.joshiejack.husbandry.data.*;
import uk.joshiejack.husbandry.entity.MobDataLoader;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.food.*;
import uk.joshiejack.husbandry.entity.traits.happiness.CarriableTrait;
import uk.joshiejack.husbandry.entity.traits.happiness.CleanableTrait;
import uk.joshiejack.husbandry.entity.traits.happiness.PettableTrait;
import uk.joshiejack.husbandry.entity.traits.happiness.TreatableTrait;
import uk.joshiejack.husbandry.entity.traits.lifestyle.*;
import uk.joshiejack.husbandry.entity.traits.product.*;
import uk.joshiejack.husbandry.inventory.MobTrackerContainer;
import uk.joshiejack.husbandry.item.HusbandryItems;
import uk.joshiejack.husbandry.note.LifespanNoteType;
import uk.joshiejack.husbandry.note.PregnancyNoteType;
import uk.joshiejack.husbandry.tileentity.HusbandryTileEntities;
import uk.joshiejack.penguinlib.inventory.AbstractBookContainer;
import uk.joshiejack.penguinlib.note.type.NoteType;
import uk.joshiejack.penguinlib.util.helpers.generic.ReflectionHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@Mod(Husbandry.MODID)
public class Husbandry {
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
        eventBus.addListener(this::setup);
        HusbandryContainers.CONTAINERS.register(eventBus);
        HusbandryBlocks.BLOCKS.register(eventBus);
        HusbandryItems.ITEMS.register(eventBus);
        HusbandryRegistries.SERIALIZERS.register(eventBus);
        HusbandrySounds.SOUNDS.register(eventBus);
        HusbandryTileEntities.TILE_ENTITIES.register(eventBus);
        HusbandryAPI.instance = new HusbandryAPIImpl();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, HusbandryConfig.create());
    }

    private void registerTrait(Class<? extends IMobTrait> data) {
        HusbandryAPI.instance.registerMobTrait(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, data.getSimpleName().replace("Trait", "")), data);
    }

    private void registerNoteType(Class<? extends NoteType> type) {
        ReflectionHelper.newInstance(type);
    }

    private void setup(FMLCommonSetupEvent event) {
        //Register Note Types
        registerNoteType(LifespanNoteType.class);
        registerNoteType(PregnancyNoteType.class);

        //Register Traits
        registerTrait(EatsBirdFeedTrait.class);
        registerTrait(EatsCatFoodTrait.class);
        registerTrait(EatsDogFoodTrait.class);
        registerTrait(EatsGrassTrait.class);
        registerTrait(EatsRabbitFoodTrait.class);
        registerTrait(EatsSlopTrait.class);
        registerTrait(CarriableTrait.class);
        registerTrait(CleanableTrait.class);
        registerTrait(PettableTrait.class);
        registerTrait(TreatableTrait.class);
        registerTrait(AstraphobicTrait.class);
        registerTrait(AquaphobicTrait.class);
        registerTrait(DiurnalTrait.class);
        registerTrait(MammalTrait.class);
        registerTrait(MortalTrait.class);
        registerTrait(PetTrait.class);
        registerTrait(BowlableTrait.class);
        registerTrait(DropsProductTrait.class);
        registerTrait(FasterProductResetTrait.class);
        registerTrait(FindsProductTrait.class);
        registerTrait(LaysEggTrait.class);
        registerTrait(MilkableTrait.class);
        registerTrait(MoreProductChanceTrait.class);
        registerTrait(MoreProductTrait.class);
        registerTrait(ShearableTrait.class);
        //Unused traits
        registerTrait(NocturnalTrait.class);
        registerTrait(RequiresFoodTrait.class);
        registerTrait(LameableTrait.class);
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

    public static class HusbandryContainers {
        public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Husbandry.MODID);
        public static final RegistryObject<ContainerType<AbstractBookContainer>> BOOK = CONTAINERS.register("mob_tracker", () -> IForgeContainerType.create((id, inv, data) -> new MobTrackerContainer(id)));
    }

    public static class HusbandrySounds {
        public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);
        public static final RegistryObject<SoundEvent> BRUSH = createSoundEvent("brush");

        private static RegistryObject<SoundEvent> createSoundEvent(@Nonnull String name) {
            return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(MODID, name)));
        }
    }

    /** API **/
    public static class HusbandryAPIImpl implements HusbandryAPI.IHusbandryAPI {
        @Override
        public void registerMobTrait(String name, Class<? extends IMobTrait> trait) {
            MobDataLoader.TRAITS.put(name, ReflectionHelper.newInstance(trait));
        }

        @Nullable
        @SuppressWarnings("unchecked")
        @Override
        public <E extends MobEntity> IMobStats<E> getStatsForEntity(E entity) {
            return (IMobStats<E>) MobStats.getStats(entity);
        }
    }

    public static class HusbandryConfig {
        public static ForgeConfigSpec.IntValue maxHappiness;
        public static ForgeConfigSpec.IntValue hungerHappinessLoss;
        public static ForgeConfigSpec.IntValue hurtHappinessLossModifier;
        public static ForgeConfigSpec.IntValue dirtyHappinessLoss;
        public static ForgeConfigSpec.IntValue wrongTreatLoss;
        public static ForgeConfigSpec.IntValue lovedGain;
        public static ForgeConfigSpec.IntValue fedGain;
        public static ForgeConfigSpec.IntValue cleanedGain;
        public static ForgeConfigSpec.IntValue genericTreatGain;
        public static ForgeConfigSpec.IntValue typeTreatGain;
        public static ForgeConfigSpec.IntValue outsideGain;
        public static ForgeConfigSpec.IntValue birthGain;

        HusbandryConfig(ForgeConfigSpec.Builder builder) {
            builder.push("Happiness Settings");
            maxHappiness = builder.defineInRange("Maximum happiness", 30000, 100, 100000000);
            hungerHappinessLoss = builder.defineInRange("Happiness lost from hunger", 1, 0, 100000);
            hurtHappinessLossModifier = builder.defineInRange("Happiness loss from hurt multiplier", 5, 0, 100);
            dirtyHappinessLoss = builder.defineInRange("Happiness lost from being unclean", 1, 0, 100000);
            wrongTreatLoss = builder.defineInRange("Happiness lost from incorrect treat type", 500, 0, 100000);
            lovedGain = builder.defineInRange("Happiness gained from petting/carrying", 100, 0, 100000);
            fedGain = builder.defineInRange("Happiness gained from hand feeding", 100, 0, 100000);
            cleanedGain = builder.defineInRange("Happiness gained from cleaning", 30, 0, 100000);
            genericTreatGain = builder.defineInRange("Happiness gained from generic treats", 100, 0, 100000);
            typeTreatGain = builder.defineInRange("Happiness gained from type treats", 250, 0, 100000);
            outsideGain = builder.defineInRange("Happiness gained from being outside in the sun", 2, 0, 100000);
            birthGain = builder.defineInRange("Happiness gained from giving birth", 100, 0, 100000);
            builder.pop();
        }

        public static ForgeConfigSpec create() {
            return new ForgeConfigSpec.Builder().configure(HusbandryConfig::new).getValue();
        }
    }
}
