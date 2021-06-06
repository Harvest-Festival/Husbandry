package uk.joshiejack.husbandry.entity.traits.food;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import uk.joshiejack.husbandry.Husbandry;

public class EatsRabbitFoodTrait extends AbstractFoodTrait {
    public static final ITag.INamedTag<Item> RABBIT_FOOD = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "rabbit_food"));


    @Override
    protected ITag.INamedTag<Item> getFoodTag() {
        return RABBIT_FOOD;
    }
}
