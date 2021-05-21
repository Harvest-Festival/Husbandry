package uk.joshiejack.husbandry.client;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.husbandry.client.gui.PageStats;
import uk.joshiejack.penguinlib.client.gui.book.Book;
import uk.joshiejack.penguinlib.client.gui.book.Tab;
import uk.joshiejack.penguinlib.inventory.AbstractBookContainer;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Husbandry.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HusbandryClient {
    public static final ResourceLocation FODDER = new ResourceLocation(Husbandry.MODID, "blocks/fodder");
    public static final ResourceLocation SLOP = new ResourceLocation(Husbandry.MODID, "blocks/slop");

    @SubscribeEvent
    public static void onMapping(TextureStitchEvent.Pre event) {
        event.addSprite(FODDER);
        event.addSprite(SLOP);
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
        ScreenManager.register(Husbandry.BOOK.get(), ((AbstractBookContainer container, PlayerInventory inv, ITextComponent text) ->
                Book.getInstance(Husbandry.MODID, container, inv, text, (Book bs) -> {
                    ITextComponent name = new TranslationTextComponent("gui." + Husbandry.MODID + ".book");
                    bs.withTab(new Tab(name)).withPage(new PageStats(name));
                })
        ));

        RenderType cutout = RenderType.cutout();
        RenderTypeLookup.setRenderLayer(HusbandryBlocks.FEEDING_TRAY.get(), cutout);
        RenderTypeLookup.setRenderLayer(HusbandryBlocks.NEST.get(), cutout);
        RenderTypeLookup.setRenderLayer(HusbandryBlocks.BOWL.get(), cutout);
        RenderTypeLookup.setRenderLayer(HusbandryBlocks.INCUBATOR.get(), cutout);
        RenderTypeLookup.setRenderLayer(HusbandryBlocks.TRUFFLE_BLOCK.get(), cutout);
    }
}
