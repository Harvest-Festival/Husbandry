package uk.joshiejack.husbandry.entity.stats;

import com.google.common.collect.Maps;
import joptsimple.internal.Strings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.ISpecies;
import uk.joshiejack.husbandry.api.trait.IMobTrait;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Species implements ISpecies {
    public static final Map<EntityType<?>, Species> TYPES = Maps.newHashMap();
    public static final Species NONE = new Species(0, 0, Items.AIR, 0, 0, 0, 0, new Products(0, new ResourceLocation(Strings.EMPTY), ItemIcon.EMPTY), new ArrayList<>());
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
        this.minimumLifespan = minimumLifespan * Husbandry.HusbandryConfig.daysPerYear.get();
        this.maximumLifespan = maximumLifespan * Husbandry.HusbandryConfig.daysPerYear.get();
        this.treat = treat;
        this.genericTreats = genericTreats;
        this.speciesTreats = speciesTreats;
        this.daysToBirth = daysToBirth;
        this.daysToMaturity = daysToMaturity;
        this.products = products;
        this.traits = traits;
    }

    public int getDaysBetweenProduct() {
        return products.getDaysBetweenProducts();
    }

    public List<ItemStack> getProduct(@Nonnull MobEntity entity, @Nullable PlayerEntity player) {
        return products.getProduct(entity, player);
    }

    @Override
    public Products getProducts() { return products; }

    @Override
    public int getMinAge() {
        return minimumLifespan;
    }

    @Override
    public int getMaxAge() {
        return maximumLifespan;
    }

    @Override
    public int getGenericTreats() {
        return genericTreats;
    }

    @Override
    public int getSpeciesTreats() {
        return speciesTreats;
    }

    @Override
    public int getDaysToBirth() {
        return daysToBirth;
    }

    @Override
    public int getDaysToMaturity() {
        return daysToMaturity;
    }

    @Override
    public Item getTreat() {
        return treat;
    }

    @Override
    public List<IMobTrait> getTraits() {
        return traits;
    }
}
