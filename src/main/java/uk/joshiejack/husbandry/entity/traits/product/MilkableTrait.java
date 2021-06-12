package uk.joshiejack.husbandry.entity.traits.product;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraftforge.items.ItemHandlerHelper;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;

import java.util.List;

public class MilkableTrait implements IInteractiveTrait {
    @Override
    public boolean onRightClick(IMobStats<?> stats, PlayerEntity player, Hand hand) {
        if (player.getItemInHand(hand).getItem() == Items.BUCKET) {
            if (stats.canProduceProduct()) {
                List<ItemStack> ret = stats.getSpecies().getProducts().getProduct(stats.getEntity(), player);
                boolean replaced = false;
                for (ItemStack stack : ret) {
                    if (stack.getItem() == Items.MILK_BUCKET && !replaced) {
                        player.getItemInHand(hand).shrink(1);
                        replaced = true;
                    }

                    ItemHandlerHelper.giveItemToPlayer(player, stack);
                }

                stats.setProduced(ret.size());
            }

            return true;
        }

        return false;
    }
}
