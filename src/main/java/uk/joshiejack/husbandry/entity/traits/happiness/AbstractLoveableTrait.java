package uk.joshiejack.husbandry.entity.traits.happiness;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IDisplayTrait;

public class AbstractLoveableTrait implements IDisplayTrait {
    private static final ResourceLocation ICONS = new ResourceLocation("minecraft", "textures/gui/icons.png");

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(MatrixStack matrix, Widget widget, int x, int y, IMobStats<?> stats) {
        Minecraft.getInstance().getTextureManager().bind(ICONS);
        for (int i = 0; i < 9; i++) {
            widget.blit(matrix, x + 24 + 10 * i, y + 6, 16, 0, 9, 9);
            if (i < stats.getHearts())
                widget.blit(matrix, x + 24 + 10 * i, y + 6, 52, 0, 9, 9);
        }
    }
}