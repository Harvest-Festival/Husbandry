package uk.joshiejack.husbandry.crafting;

import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.penguinlib.item.crafting.SimplePenguinRecipe;

import javax.annotation.Nonnull;

public class SpinningWheelRecipe extends SimplePenguinRecipe {
    public SpinningWheelRecipe(ResourceLocation resource, Ingredient ingredient, ItemStack output) {
        super(HusbandryRecipes.SPINNING_WHEEL, HusbandryRecipes.SPINNING_WHEEL_SERIALIZER.get(), resource, ingredient, output);
    }

    @Nonnull
    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(HusbandryBlocks.SPINNING_WHEEL.get());
    }

    public static SingleItemRecipeBuilder spinningwheel(Ingredient input, IItemProvider output, int amount) {
        return new SingleItemRecipeBuilder(HusbandryRecipes.SPINNING_WHEEL_SERIALIZER.get(), input, output, amount);
    }
}
