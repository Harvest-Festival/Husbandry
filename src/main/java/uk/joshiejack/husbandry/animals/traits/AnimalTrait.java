package uk.joshiejack.husbandry.animals.traits;

import com.google.common.collect.Maps;
import uk.joshiejack.husbandry.animals.traits.types.AnimalTraits;
import uk.joshiejack.penguinlib.util.helpers.generic.ReflectionHelper;

import java.util.Map;

public abstract class AnimalTrait implements AnimalTraits {
    public static final Map<String, AnimalTrait> TRAITS = Maps.newHashMap();
    private final String name;
    public AnimalTrait(String name) {
        this.name = name;
    }

    public static void registerTrait(String string, Class<? extends AnimalTrait> data) {
        AnimalTrait.TRAITS.put(string, ReflectionHelper.newInstance(ReflectionHelper.getConstructor(data, String.class), string));
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
