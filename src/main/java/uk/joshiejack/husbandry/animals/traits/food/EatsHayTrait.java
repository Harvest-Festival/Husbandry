package uk.joshiejack.husbandry.animals.traits.food;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IInteractiveTrait;

public class EatsHayTrait extends AnimalTrait implements /*IGoalTrait, */IInteractiveTrait {
    public static final ITag.INamedTag<Item> HAY = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "hay"));

    public EatsHayTrait(String name) {
        super(name);
    }

//    @Override
//    public int getPriority() {
//        return 2;
//    }
//
//    @Override
//    public Goal getGoal(AgeableEntity ageable, AnimalStats<?> stats) {
//        return new EatFromTroughGoal(ageable, stats, HAY);
//    } //TODO:

    @Override
    public boolean onRightClick(AnimalStats<?> stats, PlayerEntity player, Hand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (!HAY.contains(held.getItem()))
            return false;
        stats.feed();
        held.shrink(1);
        return true;
    }
}
