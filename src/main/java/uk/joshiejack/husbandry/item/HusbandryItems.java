package uk.joshiejack.husbandry.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.penguinlib.item.base.BookItem;
import uk.joshiejack.penguinlib.item.base.PenguinItem;

import java.util.function.BiConsumer;


@SuppressWarnings("unused")
public class HusbandryItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Husbandry.MODID);
    private static final BiConsumer<ItemStack, LivingEntity> removePotionEffects = (stack, entity) ->  {
        if (!entity.level.isClientSide) entity.curePotionEffects(stack);
    };

    // Food
    public static final RegistryObject<Item> BUTTER = ITEMS.register("butter", () -> new Item(new Item.Properties().food(HusbandryFoods.BUTTER).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> DINNER_ROLL = ITEMS.register("dinner_roll", () -> new Item(new Item.Properties().food(HusbandryFoods.DINNER_ROLL).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> BOILED_EGG = ITEMS.register("boiled_egg", () -> new Item(new Item.Properties().food(HusbandryFoods.BOILED_EGG).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> ICE_CREAM = ITEMS.register("ice_cream", () -> new Item(new Item.Properties().food(HusbandryFoods.ICE_CREAM).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> MUG_OF_MILK = ITEMS.register("mug_of_milk", () -> new PenguinItem(new PenguinItem.Properties().useAction(UseAction.DRINK).finishUsing(removePotionEffects).food(HusbandryFoods.MUG_OF_MILK).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> HOT_MILK = ITEMS.register("hot_milk", () -> new PenguinItem(new PenguinItem.Properties().useAction(UseAction.DRINK).finishUsing(removePotionEffects).food(HusbandryFoods.HOT_MILK).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> TRUFFLE = ITEMS.register("truffle", () -> new Item(new Item.Properties().food(HusbandryFoods.TRUFFLE).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> MOB_TRACKER = ITEMS.register("mob_tracker", () -> new BookItem(new Item.Properties().stacksTo(1).tab(Husbandry.TAB), Husbandry.BOOK::get));
    public static final RegistryObject<Item> BRUSH = ITEMS.register("brush", () -> new Item(new Item.Properties().stacksTo(1).durability(64).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> MIRACLE_POTION = ITEMS.register("miracle_potion", () -> new Item(new Item.Properties().stacksTo(16).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> SICKLE = ITEMS.register("sickle", () -> new SickleItem(ItemTier.STONE));

    // Animal Food
    public static final RegistryObject<Item> FODDER = ITEMS.register("fodder", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> BIRD_FEED = ITEMS.register("bird_feed", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> CAT_FOOD = ITEMS.register("cat_food", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> DOG_FOOD = ITEMS.register("dog_food", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> RABBIT_FOOD = ITEMS.register("rabbit_food", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> SLOP = ITEMS.register("slop", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    // Animal Treats
    public static final RegistryObject<Item> GENERIC_TREAT = ITEMS.register("generic_treat", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> CAT_TREAT = ITEMS.register("cat_treat", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> CHICKEN_TREAT = ITEMS.register("chicken_treat", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> COW_TREAT = ITEMS.register("cow_treat", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> DOG_TREAT = ITEMS.register("dog_treat", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> HORSE_TREAT = ITEMS.register("horse_treat", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> PIG_TREAT = ITEMS.register("pig_treat", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> RABBIT_TREAT = ITEMS.register("rabbit_treat", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> SHEEP_TREAT = ITEMS.register("sheep_treat", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> PARROT_TREAT = ITEMS.register("parrot_treat", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> LLAMA_TREAT = ITEMS.register("llama_treat", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    // Blocks
    public static final RegistryObject<Item> FEEDING_TRAY = ITEMS.register("feeding_tray", () -> new BlockItem(HusbandryBlocks.FEEDING_TRAY.get(), new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> BOWL = ITEMS.register("bowl", () -> new BlockItem(HusbandryBlocks.BOWL.get(), new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> NEST = ITEMS.register("nest", () -> new BlockItem(HusbandryBlocks.NEST.get(), new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> TROUGH = ITEMS.register("trough", () -> new BlockItem(HusbandryBlocks.TROUGH.get(), new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> INCUBATOR = ITEMS.register("incubator", () -> new BlockItem(HusbandryBlocks.INCUBATOR.get(), new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> TRUFFLE_BLOCK = ITEMS.register("truffle_block", () -> new BlockItem(HusbandryBlocks.TRUFFLE_BLOCK.get(), new Item.Properties().tab(Husbandry.TAB)));
}
