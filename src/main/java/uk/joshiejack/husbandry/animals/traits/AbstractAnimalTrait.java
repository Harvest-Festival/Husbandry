package uk.joshiejack.husbandry.animals.traits;

import com.google.common.collect.Maps;
import uk.joshiejack.husbandry.animals.traits.types.IAnimalTrait;
import uk.joshiejack.penguinlib.util.helpers.generic.ReflectionHelper;

import java.util.Map;

public abstract class AbstractAnimalTrait implements IAnimalTrait {
    public static final Map<String, AbstractAnimalTrait> TRAITS = Maps.newHashMap();
    private final String name;
    public AbstractAnimalTrait(String name) {
        this.name = name;
    }

    public static void registerTrait(String string, Class<? extends AbstractAnimalTrait> data) {
        AbstractAnimalTrait.TRAITS.put(string, ReflectionHelper.newInstance(ReflectionHelper.getConstructor(data, String.class), string));
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
