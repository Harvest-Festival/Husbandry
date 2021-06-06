package uk.joshiejack.husbandry.entity.traits.product;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.Hand;
import net.minecraftforge.common.Tags;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.AbstractMobTrait;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;

import java.util.Random;

public class ShearableTrait extends AbstractMobTrait implements IInteractiveTrait {
    public ShearableTrait(String name) {
        super(name);
    }

    @Override
    public boolean onRightClick(IMobStats<?> stats, PlayerEntity player, Hand hand) {
        MobEntity mob = stats.getEntity();
        ItemStack held = player.getItemInHand(hand);
        if (held.getItem() instanceof ShearsItem || Tags.Items.SHEARS.contains(held.getItem()) && stats.canProduceProduct()) {
            if (!player.level.isClientSide) {
                Random rand = mob.getRandom();
                for (ItemStack stack : stats.getProduct(player)) {
                    ItemEntity ent = mob.spawnAtLocation(stack, 1.0F);
                    assert ent != null;
                    ent.setDeltaMovement(ent.getDeltaMovement().add((rand.nextFloat() -
                            rand.nextFloat()) * 0.1F, rand.nextFloat() * 0.05F,
                            (rand.nextFloat() - rand.nextFloat()) * 0.1F));
                }

                stats.setProduced(5);
            }

            if (mob instanceof SheepEntity) {
                ((SheepEntity) mob).setSheared(true);
            }

            held.hurtAndBreak(1, mob, (e) -> e.broadcastBreakEvent(hand));

            return true;
        }

        return false;
    }
}
