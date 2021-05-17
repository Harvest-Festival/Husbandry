package uk.joshiejack.husbandry.animals.traits;

import com.google.common.collect.Maps;
import uk.joshiejack.husbandry.animals.traits.types.AnimalTraits;

import java.util.Map;

public abstract class AnimalTrait implements AnimalTraits {
    public static final Map<String, AnimalTrait> TRAITS = Maps.newHashMap();
    private final String name;
    public AnimalTrait(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
