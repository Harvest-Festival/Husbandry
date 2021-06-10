package uk.joshiejack.husbandry.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.api.trait.IIconTrait;
import uk.joshiejack.husbandry.api.trait.IRenderTrait;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.TraitType;

import javax.annotation.Nonnull;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MobStatsLabel extends Widget {
    private final MobStats<?> stats;

    public MobStatsLabel(MobStats<?> stats, int x, int y, ITextComponent name) {
        super(x, y, 120, 24, name);
        this.stats = stats;
    }

    @Override
    public void renderButton(@Nonnull MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0F, 0F, -1000.0F);
        InventoryScreen.renderEntityInInventory(x + 10, y + 16, 10, -180, 0, stats.getEntity());
        RenderSystem.popMatrix();
        List<IRenderTrait> traits = stats.getTraits(TraitType.RENDER);
        traits.forEach(trait -> trait.render(matrix, this, x, y, stats));
        List<IIconTrait> iconTraits = stats.getTraits(TraitType.ICON);
        for (int i = 0; i < iconTraits.size(); i++) {
            RenderSystem.pushMatrix();
            RenderSystem.scalef(0.5F, 0.5F, 0.5F);
            iconTraits.get(i).getIcon(stats).render(mc, matrix, (x + (115 - (i * 9))) * 2, (y - 3) * 2);
            RenderSystem.popMatrix();
        }

        matrix.pushPose();
        matrix.translate(0D, 0D, 110D);
        mc.font.drawShadow(matrix, stats.getEntity().getName(), x + 4, y - 8, 0xFFFFFF);
        matrix.popPose();
    }
}