package uk.joshiejack.husbandry.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.entity.DuckEggEntity;
import uk.joshiejack.husbandry.entity.HusbandryEntities;

import javax.annotation.Nonnull;

public class DuckEggItem extends Item {
    public DuckEggItem() {
        super(new Item.Properties().stacksTo(16).tab(Husbandry.TAB));
    }

    @Nonnull
    public ActionResult<ItemStack> use(@Nonnull World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!world.isClientSide) {
            DuckEggEntity eggEntity = (DuckEggEntity) HusbandryEntities.DUCK_EGG.get().create(world);
            assert eggEntity != null;
            eggEntity.setOwner(player);
            eggEntity.setItem(itemstack);
            eggEntity.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 1.5F, 1.0F);
            world.addFreshEntity(eggEntity);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.abilities.instabuild) {
            itemstack.shrink(1);
        }

        return ActionResult.sidedSuccess(itemstack, world.isClientSide());
    }
}
