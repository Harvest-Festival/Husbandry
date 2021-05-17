package uk.joshiejack.husbandry.animals.traits.happiness;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IDisplayTrait;

public class AbstractLoveableTrait extends AnimalTrait implements IDisplayTrait {
    private static final ResourceLocation ICONS = new ResourceLocation("minecraft", "textures/gui/icons.png");

    public AbstractLoveableTrait(String name) {
        super(name);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(MatrixStack matrix, Widget widget, int x, int y, AnimalStats<?> stats) {
        Minecraft.getInstance().getTextureManager().bind(ICONS);
        for (int i = 0; i < 9; i++) {
            widget.blit(matrix, x + 24 + 10 * i, y + 6, 16, 0, 9, 9);
            if (i < stats.getHearts())
                widget.blit(matrix, x + 24 + 10 * i, y + 6, 52, 0, 9, 9);
        }
    }
}
