package uk.joshiejack.husbandry.api;

import net.minecraft.item.Item;
import uk.joshiejack.husbandry.api.trait.IMobTrait;

import java.util.List;

public interface ISpecies {
    IProducts getProducts();

    int getMinAge();

    int getMaxAge();

    int getGenericTreats();

    int getSpeciesTreats();

    int getDaysToBirth();

    int getDaysToMaturity();

    Item getTreat();

    List<IMobTrait> getTraits();
}
