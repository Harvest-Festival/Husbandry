package uk.joshiejack.husbandry.item;

import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.penguinlib.item.base.BookItem;
import uk.joshiejack.penguinlib.item.base.PenguinItem;


@SuppressWarnings("unused")
public class HusbandryItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Husbandry.MODID);
    // Food
    public static final RegistryObject<Item> MAYONNAISE = ITEMS.register("mayonnaise", () -> new Item(new Item.Properties().food(HusbandryFoods.MAYONNAISE).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> BUTTER = ITEMS.register("butter", () -> new Item(new Item.Properties().food(HusbandryFoods.BUTTER).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> DINNER_ROLL = ITEMS.register("dinner_roll", () -> new Item(new Item.Properties().food(HusbandryFoods.DINNER_ROLL).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> BOILED_EGG = ITEMS.register("boiled_egg", () -> new Item(new Item.Properties().food(HusbandryFoods.BOILED_EGG).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> ICE_CREAM = ITEMS.register("ice_cream", () -> new Item(new Item.Properties().food(HusbandryFoods.ICE_CREAM).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese", () -> new Item(new Item.Properties().food(HusbandryFoods.CHEESE).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> DEVILED_EGGS = ITEMS.register("deviled_eggs", () -> new Item(new Item.Properties().food(HusbandryFoods.DEVILED_EGGS).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> DUCK_MAYONNAISE = ITEMS.register("duck_mayonnaise", () -> new Item(new Item.Properties().food(HusbandryFoods.DUCK_MAYONNAISE).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> HOT_MILK = ITEMS.register("hot_milk", () -> new PenguinItem(new PenguinItem.Properties().useAction(UseAction.DRINK).food(HusbandryFoods.HOT_MILK).tab(Husbandry.TAB)));
    public static final RegistryObject<Item> DUCK_EGG = ITEMS.register("duck_egg", DuckEggItem::new);
    public static final RegistryObject<Item> ANIMAL_TRACKER = ITEMS.register("animal_tracker", () -> new BookItem(new Item.Properties().stacksTo(1).tab(Husbandry.TAB), Husbandry.BOOK::get));
    public static final RegistryObject<Item> BRUSH = ITEMS.register("brush", BrushItem::new);
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
    public static final RegistryObject<Item> DUCK_TREAT = ITEMS.register("duck_treat", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> PARROT_TREAT = ITEMS.register("parrot_treat", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> LLAMA_TREAT = ITEMS.register("llama_treat", () -> new Item(new Item.Properties().tab(Husbandry.TAB)));
    // Blocks
    public static final RegistryObject<Item> FERMENTER = ITEMS.register("fermenter", () -> new BlockItem(HusbandryBlocks.FERMENTER.get(), new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> SPINNING_WHEEL = ITEMS.register("spinning_wheel", () -> new BlockItem(HusbandryBlocks.SPINNING_WHEEL.get(), new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> OIL_MAKER = ITEMS.register("oil_maker", () -> new BlockItem(HusbandryBlocks.OIL_MAKER.get(), new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> FEEDING_TRAY = ITEMS.register("feeding_tray", () -> new BlockItem(HusbandryBlocks.FEEDING_TRAY.get(), new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> BOWL = ITEMS.register("bowl", () -> new BlockItem(HusbandryBlocks.BOWL.get(), new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> NEST = ITEMS.register("nest", () -> new BlockItem(HusbandryBlocks.NEST.get(), new Item.Properties().tab(Husbandry.TAB)));
    //public static final RegistryObject<Item> TROUGH = ITEMS.register("trough", () -> new BlockItem(HusbandryBlocks.TROUGH.get(), new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> INCUBATOR = ITEMS.register("incubator", () -> new BlockItem(HusbandryBlocks.INCUBATOR.get(), new Item.Properties().tab(Husbandry.TAB)));
    public static final RegistryObject<Item> TRUFFLE = ITEMS.register("truffle", () -> new BlockItem(HusbandryBlocks.TRUFFLE.get(), new Item.Properties().tab(Husbandry.TAB)));
}
