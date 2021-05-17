package uk.joshiejack.husbandry.crafting;

import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.penguinlib.item.crafting.SimplePenguinRecipe;

import javax.annotation.Nonnull;

public class OilMakerRecipe extends SimplePenguinRecipe {
    public OilMakerRecipe(ResourceLocation resource, Ingredient ingredient, ItemStack output) {
        super(HusbandryRecipes.OIL_MAKER, HusbandryRecipes.OIL_MAKER_SERIALIZER.get(), resource, ingredient, output);
    }

    @Nonnull
    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(HusbandryBlocks.OIL_MAKER.get());
    }

    public static SingleItemRecipeBuilder oilmaker(Ingredient input, IItemProvider output, int amount) {
        return new SingleItemRecipeBuilder(HusbandryRecipes.OIL_MAKER_SERIALIZER.get(), input, output, amount);
    }
}
