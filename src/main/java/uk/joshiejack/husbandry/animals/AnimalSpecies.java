package uk.joshiejack.husbandry.animals;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import joptsimple.internal.Strings;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnimalSpecies {
    public static final Map<EntityType<?>, AnimalSpecies> TYPES = Maps.newHashMap();
    public static final AnimalSpecies NONE = new AnimalSpecies(0, 0, Items.AIR, 0, 0, 0, 0, new AnimalProducts(0, new ResourceLocation(Strings.EMPTY)), new ArrayList<>());
    public static int DAYS_PER_YEAR = 120;
    //Min and max length of time this animal will live
    private final int minimumLifespan; //Years IRL
    private final int maximumLifespan; //Years IRL
    private final int genericTreats;
    private final int speciesTreats;
    private final int daysToBirth; //Months (weeks if < month) IRL
    private final int daysToMaturity; //Years * 7 IRL
    private final AnimalProducts products;
    private final EnumMap<AnimalTraits.Type, List<? extends AnimalTraits>> traits;
    private final Object2BooleanMap<String> hasTraitCache = new Object2BooleanArrayMap<>();
    private final Item treat;

    public AnimalSpecies(int minimumLifespan, int maximumLifespan, Item treat, int genericTreats, int speciesTreats, int daysToBirth, int daysToMaturity, @Nonnull AnimalProducts products, List<AnimalTrait> traits) {
        this.minimumLifespan = minimumLifespan * DAYS_PER_YEAR;
        this.maximumLifespan = maximumLifespan * DAYS_PER_YEAR;
        this.treat = treat;
        this.genericTreats = genericTreats;
        this.speciesTreats = speciesTreats;
        this.daysToBirth = daysToBirth;
        this.daysToMaturity = daysToMaturity;
        this.products = products;
        this.traits = new EnumMap<>(AnimalTraits.Type.class);
        this.traits.put(AnimalTraits.Type.AI, traits.stream().filter(t-> t instanceof IGoalTrait).collect(Collectors.toList()));
        this.traits.put(AnimalTraits.Type.ACTION, traits.stream().filter(t-> t instanceof IInteractiveTrait).collect(Collectors.toList()));
        this.traits.put(AnimalTraits.Type.BI_HOURLY, traits.stream().filter(t-> t instanceof IBiHourlyTrait).collect(Collectors.toList()));
        this.traits.put(AnimalTraits.Type.DATA, traits.stream().filter(t-> t instanceof IDataTrait).collect(Collectors.toList()));
        this.traits.put(AnimalTraits.Type.NEW_DAY, traits.stream().filter(t-> t instanceof INewDayTrait).collect(Collectors.toList()));
        this.traits.put(AnimalTraits.Type.DISPLAY, traits.stream().filter(t-> t instanceof IDisplayTrait).collect(Collectors.toList()));
    }

    @SuppressWarnings("unchecked")
    public <T extends AnimalTraits> List<T> getTraits(AnimalTraits.Type type) {
        return (List<T>) traits.get(type);
    }

    public boolean hasTrait(String trait) {
        if (!hasTraitCache.containsKey(trait))
            hasTraitCache.put(trait, traits.values().stream().anyMatch(list -> list.stream().anyMatch(t -> t.getSerializedName().equals(trait))));
        return hasTraitCache.getBoolean(trait);
    }

    public AnimalProducts getProducts() { return products; }

    public int getMinAge() {
        return minimumLifespan;
    }

    public int getMaxAge() {
        return maximumLifespan;
    }

    public int getGenericTreats() {
        return genericTreats;
    }

    public int getSpeciesTreats() {
        return speciesTreats;
    }

    public int getDaysToBirth() {
        return daysToBirth;
    }

    public int getDaysToMaturity() {
        return daysToMaturity;
    }

    public Item getTreat() {
        return treat;
    }
}
