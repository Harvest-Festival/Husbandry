package uk.joshiejack.husbandry.animals.traits.product;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraftforge.items.ItemHandlerHelper;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IInteractiveTrait;

import java.util.List;

public class BowlableTrait extends AnimalTrait implements IInteractiveTrait {
    public BowlableTrait(String name) {
        super(name);
    }

    @Override
    public boolean onRightClick(AnimalStats<?> stats, PlayerEntity player, Hand hand) {
        if (stats.canProduceProduct() && player.getItemInHand(hand).getItem() == Items.BOWL) {
            List<ItemStack> ret = stats.getProduct(player);
            ret.forEach(stack -> ItemHandlerHelper.giveItemToPlayer(player, stack));
            player.getItemInHand(hand).shrink(ret.size());
            stats.setProduced(ret.size());
            return ret.size() > 0;
        }

        return false;
    }
}
