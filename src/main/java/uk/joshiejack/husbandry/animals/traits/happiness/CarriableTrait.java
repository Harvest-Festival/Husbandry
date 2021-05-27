package uk.joshiejack.husbandry.animals.traits.happiness;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.types.IInteractiveTrait;

public class CarriableTrait extends AbstractLoveableTrait implements IInteractiveTrait {
    public CarriableTrait(String name) {
        super(name);
    }

    @Override
    public boolean onRightClick(AnimalStats<?> stats, PlayerEntity player, Hand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (hand == Hand.MAIN_HAND && !player.isVehicle() && held.isEmpty() && stats.isUnloved()) {
            AgeableEntity animal = stats.getEntity();
            animal.setInvulnerable(true);
            animal.startRiding(player, true);
            return stats.setLoved();
        }

        return false;
    }
}
