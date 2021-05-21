package uk.joshiejack.husbandry.animals.traits.product;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.Hand;
import net.minecraftforge.common.Tags;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IInteractiveTrait;
import uk.joshiejack.penguinlib.util.PenguinLoader;

import java.util.Random;

@PenguinLoader("shearable")
public class ShearableTrait extends AnimalTrait implements IInteractiveTrait {
    public ShearableTrait(String name) {
        super(name);
    }

    @Override
    public boolean onRightClick(AnimalStats<?> stats, PlayerEntity player, Hand hand) {
        AgeableEntity animal = stats.getEntity();
        ItemStack held = player.getItemInHand(hand);
        if (held.getItem() instanceof ShearsItem || Tags.Items.SHEARS.contains(held.getItem()) && stats.canProduceProduct()) {
            if (!player.level.isClientSide) {
                Random rand = animal.getRandom();
                for (ItemStack stack : stats.getProduct(player)) {
                    ItemEntity ent = animal.spawnAtLocation(stack, 1.0F);
                    assert ent != null;
                    ent.setDeltaMovement(ent.getDeltaMovement().add((rand.nextFloat() -
                            rand.nextFloat()) * 0.1F, rand.nextFloat() * 0.05F,
                            (rand.nextFloat() - rand.nextFloat()) * 0.1F));
                }

                stats.setProduced(5);
            }

            if (animal instanceof SheepEntity) {
                ((SheepEntity) animal).setSheared(true);
            }

            held.hurtAndBreak(1, animal, (e) -> e.broadcastBreakEvent(hand));

            return true;
        }

        return false;
    }
}
