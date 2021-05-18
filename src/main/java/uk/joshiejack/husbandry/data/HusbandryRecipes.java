package uk.joshiejack.husbandry.data;

import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.PlayerEntityInteractionTrigger;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.item.HusbandryItems;
import uk.joshiejack.penguinlib.util.PenguinTags;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class HusbandryRecipes extends RecipeProvider {
    public HusbandryRecipes(DataGenerator generator) {
        super(generator);
    }

    private ResourceLocation rl (String name) {
        return new ResourceLocation(Husbandry.MODID, name);
    }

    private void cook(@Nonnull Consumer<IFinishedRecipe> consumer, Item input, Item output, float experience) {
        String name = input.getRegistryName().getPath();
        CookingRecipeBuilder.cooking(Ingredient.of(input), output, experience, 100, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_" + name, has(input)).save(consumer, rl(name + "_smoking"));
        CookingRecipeBuilder.cooking(Ingredient.of(input), output, experience, 600, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_" + name, has(input)).save(consumer, rl(name + "_campfire"));
        CookingRecipeBuilder.smelting(Ingredient.of(input), output, experience, 200).unlockedBy("has_" + name, has(input)).save(consumer, rl(name + "_smelting"));
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(HusbandryItems.BRUSH::get).define('W', Tags.Items.CROPS_WHEAT).define('S', Tags.Items.RODS_WOODEN).pattern(" W").pattern("S ").unlockedBy("has_wheat", has(Items.WHEAT)).save(consumer, rl("brush"));
        ShapedRecipeBuilder.shaped(HusbandryItems.MIRACLE_POTION::get).define('A', PenguinTags.APPLE).define('C', Tags.Items.CROPS_CARROT)
                .define('B', Items.GLASS_BOTTLE).define('W', Tags.Items.CROPS_WHEAT).define('F', PenguinTags.RAW_FISHES).pattern(" A ").pattern("CBW").pattern(" F ")
                .unlockedBy("entity_interaction", new PlayerEntityInteractionTrigger.Instance(EntityPredicate.AndPredicate.ANY, ItemPredicate.ANY, EntityPredicate.AndPredicate.ANY)).save(consumer, rl("miracle_potion"));
        ShapedRecipeBuilder.shaped(HusbandryItems.SICKLE::get).define('S', Items.FLINT).define('W', Tags.Items.RODS_WOODEN).pattern("SS").pattern(" W").pattern("W ").unlockedBy("has_cobble", has(Tags.Items.COBBLESTONE)).save(consumer, rl("stone_sickle"));

        ShapelessRecipeBuilder.shapeless(HusbandryItems.BIRD_FEED.get(), 12).requires(Tags.Items.SEEDS).requires(Tags.Items.SEEDS).requires(Tags.Items.SEEDS).unlockedBy("has_seeds", has(Tags.Items.SEEDS)).save(consumer, rl("bird_feed"));
        ShapelessRecipeBuilder.shapeless(HusbandryItems.CAT_FOOD.get(), 8).requires(ItemTags.FISHES).requires(Items.CHICKEN).unlockedBy("has_fish", has(Tags.Items.SEEDS)).save(consumer, rl("cat_food"));
        ShapelessRecipeBuilder.shapeless(HusbandryItems.DOG_FOOD.get(), 8).requires(Items.BEEF).requires(Items.BONE).unlockedBy("has_beef", has(Items.BEEF)).save(consumer, rl("dog_food"));
        ShapelessRecipeBuilder.shapeless(HusbandryItems.RABBIT_FOOD.get(), 12).requires(Tags.Items.CROPS_CARROT).requires(Tags.Items.CROPS_CARROT).requires(Tags.Items.CROPS_CARROT).unlockedBy("has_carrot", has(Tags.Items.CROPS_CARROT)).save(consumer, rl("rabbit_food"));
        ShapelessRecipeBuilder.shapeless(HusbandryItems.SLOP.get(), 4).requires(PenguinTags.BREAD).requires(PenguinTags.MELON).requires(Tags.Items.CROPS_WHEAT).unlockedBy("has_bread", has(PenguinTags.BREAD)).save(consumer, rl("slop"));

        //        RecipeHelper helper = new RecipeHelper(event.getRegistry(), MODID);//
//        if (HusbandryConfig.enableFeedRecipes) {
//            ResourceLocation bird_feed = new ResourceLocation(MODID, "bird_feed");
//            helper.shapelessRecipe("bird_feed_wheat", bird_feed, FEED.getStackFromEnum(ItemFeed.Feed.BIRD_FEED), Items.WHEAT_SEEDS);
//            helper.shapelessRecipe("bird_feed_melon", bird_feed, FEED.getStackFromEnum(ItemFeed.Feed.BIRD_FEED), Items.MELON_SEEDS);
//            helper.shapelessRecipe("bird_feed_pumpkin", bird_feed, FEED.getStackFromEnum(ItemFeed.Feed.BIRD_FEED), Items.PUMPKIN_SEEDS);
//            helper.shapelessRecipe("bird_feed_beetroot", bird_feed, FEED.getStackFromEnum(ItemFeed.Feed.BIRD_FEED), Items.BEETROOT_SEEDS);
//            helper.shapelessRecipe("cat_food", FEED.getStackFromEnum(ItemFeed.Feed.CAT_FOOD, 2), "fishCod", "fishSalmon");
//            helper.shapelessRecipe("dog_food", FEED.getStackFromEnum(ItemFeed.Feed.DOG_FOOD, 2), Items.CHICKEN, Items.BEEF);
//            helper.shapelessRecipe("rabbit_food", FEED.getStackFromEnum(ItemFeed.Feed.RABBIT_FOOD), "cropCarrot");
//            helper.shapelessRecipe("slop", FEED.getStackFromEnum(ItemFeed.Feed.SLOP, 3), Items.BREAD, "cropMelon", "cropWheat");
//        }
//
//        if (HusbandryConfig.enableFoodRecipes) {
//            FurnaceRecipes.instance().addSmeltingRecipe(DRINK.getStackFromEnum(ItemDrink.Drink.SMALL_MILK), DRINK.getStackFromEnum(ItemDrink.Drink.HOT_MILK, 1), 1F);
//            FurnaceRecipes.instance().addSmeltingRecipe(DRINK.getStackFromEnum(ItemDrink.Drink.MEDIUM_MILK), DRINK.getStackFromEnum(ItemDrink.Drink.HOT_MILK, 2), 1F);
//            FurnaceRecipes.instance().addSmeltingRecipe(DRINK.getStackFromEnum(ItemDrink.Drink.LARGE_MILK), DRINK.getStackFromEnum(ItemDrink.Drink.HOT_MILK, 3), 1F);
//            helper.shapelessRecipe("dinnerroll", FOOD.getStackFromEnum(ItemFood.Food.DINNERROLL), Items.BREAD, "foodButter", "egg");
//            FurnaceRecipes.instance().addSmeltingRecipe(FOOD.getStackFromEnum(ItemFood.Food.SMALL_EGG), FOOD.getStackFromEnum(ItemFood.Food.BOILED_EGG, 1), 1F);
//            FurnaceRecipes.instance().addSmeltingRecipe(FOOD.getStackFromEnum(ItemFood.Food.MEDIUM_EGG), FOOD.getStackFromEnum(ItemFood.Food.BOILED_EGG, 2), 1F);
//            FurnaceRecipes.instance().addSmeltingRecipe(FOOD.getStackFromEnum(ItemFood.Food.LARGE_EGG), FOOD.getStackFromEnum(ItemFood.Food.BOILED_EGG, 3), 1F);
//            FurnaceRecipes.instance().addSmeltingRecipe(FOOD.getStackFromEnum(ItemFood.Food.SMALL_DUCK_EGG), FOOD.getStackFromEnum(ItemFood.Food.BOILED_EGG, 1), 1F);
//            FurnaceRecipes.instance().addSmeltingRecipe(FOOD.getStackFromEnum(ItemFood.Food.MEDIUM_DUCK_EGG), FOOD.getStackFromEnum(ItemFood.Food.BOILED_EGG, 2), 1F);
//            FurnaceRecipes.instance().addSmeltingRecipe(FOOD.getStackFromEnum(ItemFood.Food.LARGE_DUCK_EGG), FOOD.getStackFromEnum(ItemFood.Food.BOILED_EGG, 3), 1F);
//            FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(Items.EGG), FOOD.getStackFromEnum(ItemFood.Food.BOILED_EGG, 2), 1F);
//
//            //Various recipes for butter
//            ResourceLocation butter = new ResourceLocation(MODID, "butter");
//            helper.shapelessRecipe("butter_small", butter, FOOD.getStackFromEnum(ItemFood.Food.BUTTER, 1), DRINK.getStackFromEnum(ItemDrink.Drink.SMALL_MILK));
//            helper.shapelessRecipe("butter_medium", butter, FOOD.getStackFromEnum(ItemFood.Food.BUTTER, 2), DRINK.getStackFromEnum(ItemDrink.Drink.MEDIUM_MILK));
//            helper.shapelessRecipe("butter_large", butter, FOOD.getStackFromEnum(ItemFood.Food.BUTTER, 3), DRINK.getStackFromEnum(ItemDrink.Drink.LARGE_MILK));
//
//            //Various ice cream recipes....
//            ResourceLocation ice_cream = new ResourceLocation(MODID, "ice_cream");
//            helper.shapelessRecipe("ice_cream_small", ice_cream, FOOD.getStackFromEnum(ItemFood.Food.ICE_CREAM, 1), Items.BOWL, DRINK.getStackFromEnum(ItemDrink.Drink.SMALL_MILK), "egg");
//            helper.shapelessRecipe("ice_cream_medium", ice_cream, FOOD.getStackFromEnum(ItemFood.Food.ICE_CREAM, 2), Items.BOWL, DRINK.getStackFromEnum(ItemDrink.Drink.MEDIUM_MILK), "egg");
//            helper.shapelessRecipe("ice_cream_large", ice_cream, FOOD.getStackFromEnum(ItemFood.Food.ICE_CREAM, 3), Items.BOWL, DRINK.getStackFromEnum(ItemDrink.Drink.LARGE_MILK), "egg");
//        }
//
//        if (HusbandryConfig.enableMachineRecipes) {
//            helper.shapedRecipe("spinning_wheel", MACHINE.getStackFromEnum(BlockMachine.Machine.SPINNING_WHEEL), " S ", "SWS", "PPP", 'S', "stickWood", 'W', "string", 'P', "plankWood");
//            helper.shapedRecipe("oil_maker", MACHINE.getStackFromEnum(BlockMachine.Machine.OIL_MAKER), "SGS", "SBS", "SPS", 'S', "stone", 'G', "blockGlass", 'B', Items.GLASS_BOTTLE, 'P', Blocks.PISTON);
//            helper.shapedRecipe("bee_hive", MACHINE.getStackFromEnum(BlockMachine.Machine.HIVE), "SSS", "L L", "LLL", 'S', "slabWood", 'L', "logWood");
//            helper.shapedRecipe("incubator", INCUBATOR.getStackFromEnum(BlockIncubator.Fill.EMPTY), "IHI", "PPP", 'I', "ingotIron", 'H', Blocks.HAY_BLOCK, 'P', "plankWood");
//            helper.shapedRecipe("fermenter", DOUBLE_MACHINE.getStackFromEnum(FermenterBlock.Machine.FERMENTER), "PPP", "G G", "LML", 'P', "slabWood", 'G', "blockGlass", 'M', Blocks.PISTON, 'L', "logWood");
//        }
//
//        if (HusbandryConfig.enableGenericTreatRecipe) {
//            helper.shapelessRecipe("treat", TREAT.getStackFromEnum(ItemTreat.Treat.GENERIC), " N ", "NSN", " N ", 'N', "nuggetGold", 'S', Items.SUGAR);
//        }
//
//        if (HusbandryConfig.enableTreatRecipes) {
//            helper.shapelessRecipe("cat_treat", TREAT.getStackFromEnum(ItemTreat.Treat.CAT), TREAT.getStackFromEnum(ItemTreat.Treat.GENERIC), Items.FISH);
//            helper.shapelessRecipe("horse_treat", TREAT.getStackFromEnum(ItemTreat.Treat.HORSE), TREAT.getStackFromEnum(ItemTreat.Treat.GENERIC), Items.APPLE);
//            helper.shapelessRecipe("cow_treat", TREAT.getStackFromEnum(ItemTreat.Treat.COW), TREAT.getStackFromEnum(ItemTreat.Treat.GENERIC), Items.POTATO);
//            helper.shapelessRecipe("sheep_treat", TREAT.getStackFromEnum(ItemTreat.Treat.SHEEP), TREAT.getStackFromEnum(ItemTreat.Treat.GENERIC), Items.WHEAT);
//            helper.shapelessRecipe("chicken_treat", TREAT.getStackFromEnum(ItemTreat.Treat.CHICKEN), TREAT.getStackFromEnum(ItemTreat.Treat.GENERIC), Items.PUMPKIN_SEEDS);
//            helper.shapelessRecipe("rabbit_treat", TREAT.getStackFromEnum(ItemTreat.Treat.RABBIT), TREAT.getStackFromEnum(ItemTreat.Treat.GENERIC), Items.CARROT);
//            helper.shapelessRecipe("pig_treat", TREAT.getStackFromEnum(ItemTreat.Treat.PIG), TREAT.getStackFromEnum(ItemTreat.Treat.GENERIC), Blocks.PUMPKIN);
//            helper.shapelessRecipe("dog_treat", TREAT.getStackFromEnum(ItemTreat.Treat.DOG), TREAT.getStackFromEnum(ItemTreat.Treat.GENERIC), Items.BONE);
//        }
//
//        if (HusbandryConfig.enableFeederRecipes) {
//            helper.shapedRecipe("feeder_tray", TRAY.getStackFromEnum(BlockTray.Tray.FEEDER_EMPTY), "BSB", 'B', "plankWood", 'S', "slabWood");
//            helper.shapedRecipe("feeder_trough", TROUGH.getStackFromEnum(BlockTrough.Section.SINGLE), "P P", "PPP", "L L", 'P', "plankWood", 'L', "logWood");
//            helper.shapedRecipe("feeder_bowl", TRAY.getStackFromEnum(BlockTray.Tray.BOWL_EMPTY), " S ", "S S", " S ", 'S', "slabWood");
//        }
//
//        if (HusbandryConfig.enableNestRecipe) {
//            helper.shapedRecipe("nest", TRAY.getStackFromEnum(BlockTray.Tray.NEST_EMPTY), "PHP", "PPP", 'H', Blocks.HAY_BLOCK, 'P', "plankWood");
//        }
    }
}
