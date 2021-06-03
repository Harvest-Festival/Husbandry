package uk.joshiejack.husbandry.data.builders;

import com.google.gson.JsonObject;
import net.minecraft.entity.EntityType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.RangedInteger;
import uk.joshiejack.husbandry.crafting.HusbandryRegistries;
import uk.joshiejack.husbandry.crafting.IncubatorRecipe;
import uk.joshiejack.penguinlib.data.generators.builders.SimplePenguinRegistryBuilder;

import java.util.Objects;

public class IncubatorRecipeBuilder extends SimplePenguinRegistryBuilder<IncubatorRecipe> {
    private final EntityType<?> entity;
    private final RangedInteger range;

    public IncubatorRecipeBuilder(Ingredient ingredient, EntityType<?> type, RangedInteger range) {
        super(HusbandryRegistries.INCUBATOR_SERIALIZER.get(), ingredient);
        this.entity = type;
        this.range = range;
    }

    public static IncubatorRecipeBuilder incubate(Ingredient item, EntityType<?> entity, int min ,int max) {
        return new IncubatorRecipeBuilder(item, entity, RangedInteger.of(min, max));
    }

    @Override
    public void serializeRecipeData(JsonObject json) {
        super.serializeRecipeData(json);
        json.addProperty("entity", Objects.requireNonNull(entity.getRegistryName()).toString());
        if (range.getMinInclusive() == range.getMaxInclusive())
            json.addProperty("amount", range.getMinInclusive());
        else {
            json.addProperty("min amount", range.getMinInclusive());
            json.addProperty("max amount", range.getMaxInclusive());
        }
    }
}