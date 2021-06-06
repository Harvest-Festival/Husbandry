package uk.joshiejack.husbandry.entity.stats;

import com.google.common.collect.Maps;
import joptsimple.internal.Strings;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import uk.joshiejack.husbandry.entity.traits.types.IMobTrait;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Species {
    public static final Map<EntityType<?>, Species> TYPES = Maps.newHashMap();
    public static final Species NONE = new Species(0, 0, Items.AIR, 0, 0, 0, 0, new Products(0, new ResourceLocation(Strings.EMPTY)), new ArrayList<>());
    public static int DAYS_PER_YEAR = 120;
    //Min and max length of time this mob will live
    private final int minimumLifespan; //Years IRL
    private final int maximumLifespan; //Years IRL
    private final int genericTreats;
    private final int speciesTreats;
    private final int daysToBirth; //Months (weeks if < month) IRL
    private final int daysToMaturity; //Years * 7 IRL
    private final Products products;
    private final List<IMobTrait> traits;
    private final Item treat;

    public Species(int minimumLifespan, int maximumLifespan, Item treat, int genericTreats, int speciesTreats, int daysToBirth, int daysToMaturity, @Nonnull Products products, List<IMobTrait> traits) {
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

    public Products getProducts() { return products; }

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

    public List<IMobTrait> getTraits() {
        return traits;
    }
}
