package uk.joshiejack.husbandry.entity.traits.happiness;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IRenderTrait;

public class AbstractLoveableTrait implements IRenderTrait {
    private static final ResourceLocation MINECRAFT_ICONS = new ResourceLocation("minecraft", "textures/gui/icons.png");
    private static final ResourceLocation HUSBANDRY_ICONS = new ResourceLocation(Husbandry.MODID, "textures/gui/icons.png");

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(MatrixStack matrix, Widget widget, int x, int y, IMobStats<?> stats) {
        for (int i = 0; i < 10; i++) {
            Minecraft.getInstance().getTextureManager().bind(i >= stats.getMaxHearts() ? HUSBANDRY_ICONS : MINECRAFT_ICONS);
            widget.blit(matrix, x + 24 + 10 * i, y + 6, 16, 0, 9, 9);
            if (i < stats.getHearts())
                widget.blit(matrix, x + 24 + 10 * i, y + 6, 52, 0, 9, 9);
        }
    }
}
