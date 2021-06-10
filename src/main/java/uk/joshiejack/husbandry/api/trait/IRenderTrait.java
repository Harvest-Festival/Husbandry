package uk.joshiejack.husbandry.api.trait;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.api.IMobStats;

public interface IRenderTrait extends IMobTrait {
    @OnlyIn(Dist.CLIENT)
    void render(MatrixStack matrix, Widget widget, int x, int y, IMobStats<?> stats);
}