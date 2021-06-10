package uk.joshiejack.husbandry.client;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.husbandry.client.gui.PageStats;
import uk.joshiejack.husbandry.client.renderer.EggSupplierBakedModel;
import uk.joshiejack.husbandry.tileentity.IncubatorTileEntity;
import uk.joshiejack.husbandry.tileentity.NestTileEntity;
import uk.joshiejack.penguinlib.client.gui.book.Book;
import uk.joshiejack.penguinlib.client.gui.book.tab.NotesTab;
import uk.joshiejack.penguinlib.client.gui.book.tab.Tab;
import uk.joshiejack.penguinlib.inventory.AbstractBookContainer;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.TextureIcon;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Husbandry.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HusbandryClient {
    public static final ResourceLocation FODDER = new ResourceLocation(Husbandry.MODID, "block/fodder");
    public static final ResourceLocation SLOP = new ResourceLocation(Husbandry.MODID, "block/slop");

    @SubscribeEvent
    public static void onMapping(TextureStitchEvent.Pre event) {
        event.addSprite(FODDER);
        event.addSprite(SLOP);
    }

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
        ScreenManager.register(Husbandry.HusbandryContainers.BOOK.get(), ((AbstractBookContainer container, PlayerInventory inv, ITextComponent text) ->
                Book.getInstance(Husbandry.MODID, container, inv, text, (Book bs) -> {
                    ITextComponent stats = new TranslationTextComponent("gui." + Husbandry.MODID + ".stats");
                    ITextComponent notes = new TranslationTextComponent("gui." + Husbandry.MODID + ".notes");
                    bs.withTab(new Tab(stats, PageStats.ICON)).withPage(new PageStats(stats));
                    bs.withTab(new NotesTab(notes, new TextureIcon(Icon.DEFAULT_LOCATION, 0, 0))
                            .withCategory(new ResourceLocation(Husbandry.MODID, "care_category")));
                })
        ));

        RenderType cutout = RenderType.cutout();
        RenderTypeLookup.setRenderLayer(HusbandryBlocks.FEEDING_TRAY.get(), cutout);
        RenderTypeLookup.setRenderLayer(HusbandryBlocks.NEST.get(), cutout);
        RenderTypeLookup.setRenderLayer(HusbandryBlocks.BOWL.get(), cutout);
        RenderTypeLookup.setRenderLayer(HusbandryBlocks.INCUBATOR.get(), cutout);
        RenderTypeLookup.setRenderLayer(HusbandryBlocks.TRUFFLE_BLOCK.get(), cutout);
        RenderTypeLookup.setRenderLayer(HusbandryBlocks.TROUGH.get(), cutout);
    }

    public static final ResourceLocation DEFAULT_NEST_EGG = new ResourceLocation(Husbandry.MODID, "block/nest_egg");
    public static final ResourceLocation DEFAULT_INCUBATOR_EGG = new ResourceLocation(Husbandry.MODID, "block/incubator_egg");

    @SubscribeEvent
    public static void loadModels(ModelRegistryEvent event) {
        ModelLoader.addSpecialModel(DEFAULT_NEST_EGG);
        ModelLoader.addSpecialModel(DEFAULT_INCUBATOR_EGG);
    }

    private static void replaceWithEggSupplier(String model, ModelBakeEvent event, Map<Item, ResourceLocation> overrides, ModelProperty<ItemStack> property) {
        ResourceLocation key = new ModelResourceLocation(model);
        IBakedModel nest = event.getModelManager().getModel(key);
        if (!(event.getModelRegistry().get(key) instanceof EggSupplierBakedModel))
            event.getModelRegistry().put(key, new EggSupplierBakedModel(nest, overrides, property));
    }

    @SubscribeEvent
    public static void onBaking(ModelBakeEvent event) {
        //TODO: Allow for collecting custom model resources for each item from somewhere
        Map<Item, ResourceLocation> nest = new HashMap<>();
        nest.put(Items.EGG, DEFAULT_NEST_EGG);
        Map<Item, ResourceLocation> incubator = new HashMap<>();
        incubator.put(Items.EGG, DEFAULT_INCUBATOR_EGG);
        replaceWithEggSupplier("husbandry:nest#", event, nest, NestTileEntity.ITEM_STACK);
        replaceWithEggSupplier("husbandry:incubator#facing=north", event, incubator, IncubatorTileEntity.ITEM_STACK);
        replaceWithEggSupplier("husbandry:incubator#facing=east", event, incubator, IncubatorTileEntity.ITEM_STACK);
        replaceWithEggSupplier("husbandry:incubator#facing=west", event, incubator, IncubatorTileEntity.ITEM_STACK);
        replaceWithEggSupplier("husbandry:incubator#facing=south", event, incubator, IncubatorTileEntity.ITEM_STACK);
    }
}