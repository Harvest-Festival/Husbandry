package uk.joshiejack.husbandry.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.penguinlib.item.base.AbstractSickleItem;

public class SickleItem extends AbstractSickleItem {
    public SickleItem(ItemTier tier) {
        super(tier, new Item.Properties().tab(Husbandry.TAB));
    }
}