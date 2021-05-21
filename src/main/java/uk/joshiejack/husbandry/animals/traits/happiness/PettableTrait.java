package uk.joshiejack.husbandry.animals.traits.happiness;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.types.IInteractiveTrait;
import uk.joshiejack.penguinlib.util.PenguinLoader;

@PenguinLoader("pettable")
public class PettableTrait extends AbstractLoveableTrait implements IInteractiveTrait {
    public PettableTrait(String name) {
        super(name);
    }

    @Override
    public boolean onRightClick(AnimalStats<?> stats, PlayerEntity player, Hand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (held.isEmpty() && stats.isUnloved()) {
            return stats.setLoved();
        }

        return false;
    }
}
