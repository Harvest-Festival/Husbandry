package uk.joshiejack.husbandry.crafting;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.joshiejack.husbandry.Husbandry;

@SuppressWarnings("unused")
public class HusbandryRegistries {
    public static final DeferredRegister<IRecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Husbandry.MODID);
    public static final RegistryObject<IRecipeSerializer<IncubatorRecipe>> INCUBATOR_SERIALIZER = SERIALIZERS.register("incubator", IncubatorRecipe.Serializer::new);
    public static final IRecipeType<IncubatorRecipe> INCUBATOR = IRecipeType.register(Husbandry.MODID + ":incubator");
}
