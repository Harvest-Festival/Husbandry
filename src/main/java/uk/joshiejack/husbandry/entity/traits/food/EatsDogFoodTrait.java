package uk.joshiejack.husbandry.entity.traits.food;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import uk.joshiejack.husbandry.Husbandry;

public class EatsDogFoodTrait extends AbstractFoodTrait {
    public static final ITag.INamedTag<Item> DOG_FOOD = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "dog_food"));


    @Override
    protected ITag.INamedTag<Item> getFoodTag() {
        return DOG_FOOD;
    }
}
