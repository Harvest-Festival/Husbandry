package uk.joshiejack.husbandry.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.ChickenModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.entity.DuckEntity;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class DuckRenderer extends MobRenderer<DuckEntity, ChickenModel<DuckEntity>> {
    private static final ResourceLocation DUCK_TEXTURE = new ResourceLocation(Husbandry.MODID, "textures/entity/duck.png");

    public DuckRenderer(EntityRendererManager rm) {
        super(rm, new ChickenModel<>(), 0.3F);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull DuckEntity entity) {
        return DUCK_TEXTURE;
    }

    @Override
    protected float getBob(@Nonnull DuckEntity entity, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, entity.oFlap, entity.flap);
        float f1 = MathHelper.lerp(partialTicks, entity.oFlapSpeed, entity.flapSpeed);
        return (MathHelper.sin(f) + 1.0F) * f1;
    }
}