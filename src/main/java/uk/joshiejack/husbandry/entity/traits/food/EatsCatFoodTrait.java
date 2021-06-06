package uk.joshiejack.husbandry.entity.traits.food;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import uk.joshiejack.husbandry.Husbandry;

public class EatsCatFoodTrait extends AbstractFoodTrait {
    public static final ITag.INamedTag<Item> CAT_FOOD = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "cat_food"));


    @Override
    protected ITag.INamedTag<Item> getFoodTag() {
        return CAT_FOOD;
    }
}
