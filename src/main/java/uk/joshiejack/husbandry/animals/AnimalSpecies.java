package uk.joshiejack.husbandry.animals;

import com.google.common.collect.Maps;
import joptsimple.internal.Strings;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import uk.joshiejack.husbandry.animals.traits.types.IAnimalTrait;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private final List<IAnimalTrait> traits;
    private final Item treat;

    public AnimalSpecies(int minimumLifespan, int maximumLifespan, Item treat, int genericTreats, int speciesTreats, int daysToBirth, int daysToMaturity, @Nonnull AnimalProducts products, List<IAnimalTrait> traits) {
        this.minimumLifespan = minimumLifespan * DAYS_PER_YEAR;
        this.maximumLifespan = maximumLifespan * DAYS_PER_YEAR;
        this.treat = treat;
        this.genericTreats = genericTreats;
        this.speciesTreats = speciesTreats;
        this.daysToBirth = daysToBirth;
        this.daysToMaturity = daysToMaturity;
        this.products = products;
        this.traits = traits;
    }

    @SuppressWarnings("unchecked")

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

    public List<IAnimalTrait> getTraits() {
        return traits;
    }
}
