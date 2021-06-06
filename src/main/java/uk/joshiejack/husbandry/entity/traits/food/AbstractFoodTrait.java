package uk.joshiejack.husbandry.entity.traits.food;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.Hand;
import uk.joshiejack.husbandry.entity.ai.EatFoodGoal;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.AbstractMobTrait;
import uk.joshiejack.husbandry.entity.traits.types.IInteractiveTrait;
import uk.joshiejack.husbandry.entity.traits.types.IJoinWorldTrait;

public abstract class AbstractFoodTrait extends AbstractMobTrait implements IJoinWorldTrait, IInteractiveTrait {
    public AbstractFoodTrait(String name) {
        super(name);
    }

    protected abstract ITag.INamedTag<Item> getFoodTag();

    @Override
    public void onJoinWorld(MobStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(3, new EatFoodGoal(stats.getEntity(), stats, getFoodTag()));
    }

    @Override
    public boolean onRightClick(MobStats<?> stats, PlayerEntity player, Hand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (!getFoodTag().contains(held.getItem()))
            return false;
        stats.feed();
        held.shrink(1);
        return true;
    }
}
