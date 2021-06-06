package uk.joshiejack.husbandry.animals.traits.product;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraftforge.items.ItemHandlerHelper;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AbstractAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IInteractiveTrait;

import java.util.List;

public class MilkableTrait extends AbstractAnimalTrait implements IInteractiveTrait {
    public MilkableTrait(String name) {
        super(name);
    }

    @Override
    public boolean onRightClick(AnimalStats<?> stats, PlayerEntity player, Hand hand) {
        if (stats.canProduceProduct() && player.getItemInHand(hand).getItem() == Items.BUCKET) {
            List<ItemStack> ret = stats.getProduct(player);
            boolean replaced = false;
            for (ItemStack stack: stats.getProduct(player)) {
                if (stack.getItem() == Items.MILK_BUCKET && !replaced) {
                    player.setItemInHand(hand, new ItemStack(Items.MILK_BUCKET));
                    replaced = true;
                }

                ItemHandlerHelper.giveItemToPlayer(player, stack);
            }

            stats.setProduced(ret.size());
            return ret.size() > 0;
        }

        return false;
    }
}
