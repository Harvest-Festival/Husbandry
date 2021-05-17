package uk.joshiejack.husbandry.crafting;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.penguinlib.item.crafting.SimplePenguinRecipe;

@SuppressWarnings("unused")
public class HusbandryRecipes {
    public static final DeferredRegister<IRecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Husbandry.MODID);
    public static final RegistryObject<IRecipeSerializer<FermenterRecipe>> FERMENTER_SERIALIZER = SERIALIZERS.register("fermenter", () -> new SimplePenguinRecipe.Serializer<>(FermenterRecipe::new));
    public static final RegistryObject<IRecipeSerializer<OilMakerRecipe>> OIL_MAKER_SERIALIZER = SERIALIZERS.register("oil_maker", () -> new SimplePenguinRecipe.Serializer<>(OilMakerRecipe::new));
    public static final RegistryObject<IRecipeSerializer<SpinningWheelRecipe>> SPINNING_WHEEL_SERIALIZER = SERIALIZERS.register("spinning_wheel", () -> new SimplePenguinRecipe.Serializer<>(SpinningWheelRecipe::new));
    public static final RegistryObject<IRecipeSerializer<IncubatorRecipe>> INCUBATOR_SERIALIZER = SERIALIZERS.register("incubator", IncubatorRecipe.Serializer::new);
    public static final IRecipeType<FermenterRecipe> FERMENTER = IRecipeType.register(Husbandry.MODID + ":fermenter");
    public static final IRecipeType<OilMakerRecipe> OIL_MAKER = IRecipeType.register(Husbandry.MODID + ":oil_maker");
    public static final IRecipeType<SpinningWheelRecipe> SPINNING_WHEEL = IRecipeType.register(Husbandry.MODID + ":spinning_wheel");
    public static final IRecipeType<IncubatorRecipe> INCUBATOR = IRecipeType.register(Husbandry.MODID + ":incubator");
}
