package uk.joshiejack.husbandry.client.renderer;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class EggSupplierBakedModel extends BakedModelWrapper<IBakedModel> {
    private final Map<Item, Map<Direction, List<BakedQuad>>> QUADS = new HashMap<>();
    private final Map<Item, ResourceLocation> overrides;
    private final ModelProperty<ItemStack> property;

    public EggSupplierBakedModel(IBakedModel originalModel, Map<Item, ResourceLocation> overrides, ModelProperty<ItemStack> property) {
        super(originalModel);
        this.overrides = overrides;
        this.property = property;
    }

    private Item defaultItem(Item item) {
        return overrides.containsKey(item) ? item : Items.EGG;
    }

    private Map<Direction, List<BakedQuad>> getQuads(Item item) {
        if (!QUADS.containsKey(item))
            QUADS.put(item, new HashMap<>());
        return QUADS.get(item);
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random random, @Nonnull IModelData data) {
        if (state != null && data.hasProperty(property)) {
            ItemStack stack = data.getData(property);
            if (stack != null && !stack.isEmpty()) {
                Map<Direction, List<BakedQuad>> map = getQuads(defaultItem(stack.getItem()));
                if (map.containsKey(side)) return map.get(side);
                else {
                    List<BakedQuad> quads = Lists.newArrayList(super.getQuads(state, side, random, data));
                    Minecraft mc = Minecraft.getInstance();
                    if (!overrides.containsKey(stack.getItem())) {
                        IBakedModel item = mc.getItemRenderer().getModel(stack, mc.level, mc.player);
                        quads.addAll(item.getQuads(null, side, random));
                    } else quads.addAll(mc.getModelManager().getModel(overrides.get(stack.getItem())).getQuads(null, side, random));
                    map.put(side, quads);
                    return quads;
                }
            }
        }

        return super.getQuads(state, side, random, data);
    }
}