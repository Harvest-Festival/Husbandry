package uk.joshiejack.husbandry.entity.traits;

import com.google.common.collect.Maps;
import uk.joshiejack.husbandry.entity.traits.types.IMobTrait;
import uk.joshiejack.penguinlib.util.helpers.generic.ReflectionHelper;

import java.util.Map;

public abstract class AbstractMobTrait implements IMobTrait {
    public static final Map<String, AbstractMobTrait> TRAITS = Maps.newHashMap();
    private final String name;
    public AbstractMobTrait(String name) {
        this.name = name;
    }

    public static void registerTrait(String string, Class<? extends AbstractMobTrait> data) {
        AbstractMobTrait.TRAITS.put(string, ReflectionHelper.newInstance(ReflectionHelper.getConstructor(data, String.class), string));
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
