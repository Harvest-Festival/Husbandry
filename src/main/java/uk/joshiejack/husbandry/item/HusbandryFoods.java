package uk.joshiejack.husbandry.item;

import net.minecraft.item.Food;

public class HusbandryFoods {
    public static final Food BUTTER = new Food.Builder().nutrition(1).saturationMod(0.2F).build();
    public static final Food DINNER_ROLL = new Food.Builder().nutrition(6).saturationMod(0.8F).build();
    public static final Food BOILED_EGG = new Food.Builder().nutrition(2).saturationMod(1F).build();
    public static final Food ICE_CREAM = new Food.Builder().nutrition(5).saturationMod(0.3F).build();
    public static final Food CHEESE = new Food.Builder().nutrition(3).saturationMod(0.85F).build();
    public static final Food HOT_MILK = new Food.Builder().nutrition(3).saturationMod(0.2F).build();
}
