package uk.joshiejack.husbandry.entity.traits.food;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.Hand;
import net.minecraftforge.common.util.Lazy;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IIconTrait;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;
import uk.joshiejack.husbandry.api.trait.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.ai.EatFoodGoal;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.TagIcon;

public abstract class AbstractFoodTrait implements IJoinWorldTrait, IInteractiveTrait, IIconTrait {
    private final Lazy<Icon> icon = Lazy.of(() -> new TagIcon(getFoodTag()));

    protected abstract ITag.INamedTag<Item> getFoodTag();

    @Override
    public Icon getIcon(IMobStats<?> stats) {
        return stats.isHungry() ? icon.get().shadowed() : icon.get();
    }

    @Override
    public void onJoinWorld(IMobStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(3, new EatFoodGoal(stats.getEntity(), stats, getFoodTag()));
    }

    @Override
    public boolean onRightClick(IMobStats<?> stats, PlayerEntity player, Hand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (!getFoodTag().contains(held.getItem()))
            return false;
        stats.feed();
        held.shrink(1);
        return true;
    }
}
