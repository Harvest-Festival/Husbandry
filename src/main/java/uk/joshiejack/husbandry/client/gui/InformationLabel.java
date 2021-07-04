package uk.joshiejack.husbandry.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class InformationLabel extends Widget {
    public InformationLabel(int x, int y) {
        super(x, y, 120, 24, new TranslationTextComponent("gui.husbandry.noentity"));
    }

    @Override
    public void renderButton(@Nonnull MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        Minecraft.getInstance().font.drawWordWrap(getMessage(), x + 22, y + 8, 120, 4210752);
    }
}