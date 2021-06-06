package uk.joshiejack.husbandry.entity.traits.product;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraftforge.items.ItemHandlerHelper;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.AbstractMobTrait;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;

import java.util.List;

public class BowlableTrait extends AbstractMobTrait implements IInteractiveTrait {
    public BowlableTrait(String name) {
        super(name);
    }

    @Override
    public boolean onRightClick(IMobStats<?> stats, PlayerEntity player, Hand hand) {
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
