package uk.joshiejack.husbandry.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.Widget;
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
    private final List<IDisplayTrait> traits;
    private final AnimalStats<?> stats;

    public AnimalStatsLabel(AnimalStats<?> stats, int x, int y, ITextComponent name) {
        super(x, y, 120, 16, name);
        this.traits = stats.getType().getTraits(AnimalTraits.Type.DISPLAY);
        this.stats = stats;
    }

    @Override
    public void renderButton(@Nonnull MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        InventoryScreen.renderEntityInInventory(x + 10, y + 16, 10, -180, 0, stats.getEntity());
        Minecraft.getInstance().font.drawShadow(matrix, stats.getEntity().getName(), x + 4, y - 7, 0xFFFFFF);
        traits.forEach(trait -> trait.render(matrix, this, x, y, stats));
    }
}