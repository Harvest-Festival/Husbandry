package uk.joshiejack.husbandry.api.trait;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public abstract class AbstractMobTrait implements IStringSerializable {
    private final String name;

    /**
     *  Any class that extends this MUST have a constructor that takes a string
     *  Traits are copied via reflection to different animal copies (if they implement IDataTrait)
     * @param name  the name of this trait
     */
    public AbstractMobTrait(String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return name;
    }
}