package uk.joshiejack.husbandry.entity.traits.happiness;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IIconTrait;
import uk.joshiejack.husbandry.api.trait.IRenderTrait;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.TextureIcon;

public class AbstractLoveableTrait implements IRenderTrait, IIconTrait {
    private static final ResourceLocation MINECRAFT_ICONS = new ResourceLocation("minecraft", "textures/gui/icons.png");
    public static final ResourceLocation HUSBANDRY_ICONS = new ResourceLocation(Husbandry.MODID, "textures/gui/icons.png");
    public static final Icon ICON = new TextureIcon(HUSBANDRY_ICONS, 32, 0);

    @Override
    public Icon getIcon(IMobStats<?> stats) {
        return stats.isUnloved() ? ICON.shadowed() : ICON;
    }

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
