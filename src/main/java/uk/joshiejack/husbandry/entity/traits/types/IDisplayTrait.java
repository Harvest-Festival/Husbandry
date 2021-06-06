package uk.joshiejack.husbandry.entity.traits.types;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.entity.stats.MobStats;

public interface IDisplayTrait extends IMobTrait {
    @OnlyIn(Dist.CLIENT)
    void render(MatrixStack matrix, Widget widget, int x, int y, MobStats<?> stats);
}
