package uk.joshiejack.husbandry.animals.traits.types;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;

public interface IDisplayTrait extends AnimalTraits {
    @OnlyIn(Dist.CLIENT)
    void render(MatrixStack matrix, Widget widget, int x, int y, AnimalStats<?> stats);
}
