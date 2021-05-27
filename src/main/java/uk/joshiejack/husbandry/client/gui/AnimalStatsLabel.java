package uk.joshiejack.husbandry.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.types.AnimalTraits;
import uk.joshiejack.husbandry.animals.traits.types.IDisplayTrait;

import javax.annotation.Nonnull;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class AnimalStatsLabel extends Widget {
    private static final ResourceLocation ICONS = new ResourceLocation("minecraft", "textures/gui/icons.png");
    private final List<IDisplayTrait> traits;
    private final AnimalStats<?> stats;

    public AnimalStatsLabel(AnimalStats<?> stats, int x, int y, ITextComponent name) {
        super(x, y, 120, 16, name);
        this.traits = stats.getType().getTraits(AnimalTraits.Type.DISPLAY);
        this.stats = stats;
    }

    @Override
    public void renderButton(@Nonnull MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        setBlitOffset(100);
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)0F, (float)0F, -1000.0F);
        InventoryScreen.renderEntityInInventory(x + 10, y + 16, 10, -180, 0, stats.getEntity());
        RenderSystem.popMatrix();
        setBlitOffset(0);
        mc.font.drawShadow(matrix, stats.getEntity().getName(), x + 4, y - 8, 0xFFFFFF);
        traits.forEach(trait -> trait.render(matrix, this, x, y, stats));
        setBlitOffset(100);
        //Draw hearts
        mc.getTextureManager().bind(ICONS);
        for (int i = 0; i < 10; i++) {
            blit(matrix, x + 24 + 10 * i, y + 6, 16, 0, 9, 9);
            if (i < stats.getHearts()) {
                blit(matrix, x + 24 + + 10 * i, y + 6, 52, 0, 9, 9);
            }
        }

        setBlitOffset(0);
    }
}