package uk.joshiejack.husbandry.entity.traits.happiness;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.types.IInteractiveTrait;

public class CarriableTrait extends AbstractLoveableTrait implements IInteractiveTrait {
    public CarriableTrait(String name) {
        super(name);
    }

    @Override
    public boolean onRightClick(MobStats<?> stats, PlayerEntity player, Hand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (hand == Hand.MAIN_HAND && !player.isVehicle() && held.isEmpty() && stats.isUnloved()) {
            MobEntity entity = stats.getEntity();
            entity.setInvulnerable(true);
            entity.startRiding(player, true);
            return stats.setLoved();
        }

        return false;
    }
}
