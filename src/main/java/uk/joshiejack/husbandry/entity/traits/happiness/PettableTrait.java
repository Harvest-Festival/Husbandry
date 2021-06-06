package uk.joshiejack.husbandry.entity.traits.happiness;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;

public class PettableTrait extends AbstractLoveableTrait implements IInteractiveTrait {
    @Override
    public boolean onRightClick(IMobStats<?> stats, PlayerEntity player, Hand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (held.isEmpty() && stats.isUnloved()) {
            return stats.setLoved();
        }

        return false;
    }
}
