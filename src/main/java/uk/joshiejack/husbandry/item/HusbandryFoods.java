package uk.joshiejack.husbandry.item;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class HusbandryFoods {
    public static final Food MAYONNAISE = new Food.Builder().nutrition(4).saturationMod(0.75F).build();
    public static final Food BUTTER = new Food.Builder().nutrition(1).saturationMod(0.2F).build();
    public static final Food DINNER_ROLL = new Food.Builder().nutrition(6).saturationMod(0.8F).build();
    public static final Food BOILED_EGG = new Food.Builder().nutrition(2).saturationMod(1F).build();
    public static final Food ICE_CREAM = new Food.Builder().nutrition(5).saturationMod(0.3F).build();
    public static final Food CHEESE = new Food.Builder().nutrition(3).saturationMod(0.85F).build();
    public static final Food DEVILED_EGGS = new Food.Builder().nutrition(8).saturationMod(0.8F).build();
    public static final Food DUCK_MAYONNAISE = new Food.Builder().nutrition(4).saturationMod(0.8F).build();
    public static final Food HOT_MILK = new Food.Builder().nutrition(3).saturationMod(0.2F).build();
    public static final Food DUCK = new Food.Builder().nutrition(3).saturationMod(0.4F).effect(() -> new EffectInstance(Effects.HUNGER, 700, 0), 0.3F).meat().build();
    public static final Food COOKED_DUCK = new Food.Builder().nutrition(7).saturationMod(0.65F).meat().build();
}
