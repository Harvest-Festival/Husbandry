package uk.joshiejack.husbandry.animals.traits.food;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IGoalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IInteractiveTrait;
import uk.joshiejack.husbandry.entity.ai.AbstractMoveToBlockGoal;
import uk.joshiejack.husbandry.entity.ai.EatFoodGoal;
import uk.joshiejack.husbandry.entity.ai.EatGrassBlockGoal;
import uk.joshiejack.husbandry.entity.ai.EatTallGrassGoal;

import java.util.Objects;

public class EatsGrassTrait extends AnimalTrait implements IGoalTrait, IInteractiveTrait {
    public static final ITag.INamedTag<Item> HAY = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "hay"));

    public EatsGrassTrait(String name) {
        super(name);
    }

    @Override
    public void modifyGoals(AnimalStats<?> stats, GoalSelector selector) {
        if (stats.getEntity() instanceof SheepEntity) {
            selector.removeGoal(Objects.requireNonNull(ObfuscationReflectionHelper.getPrivateValue(SheepEntity.class, (SheepEntity) stats.getEntity(), "field_146087_bs")));
        }

        selector.addGoal(3, new EatFoodGoal(stats.getEntity(), stats, HAY, AbstractMoveToBlockGoal.Orientation.BESIDE, 16));
        selector.addGoal(3, new EatTallGrassGoal(stats.getEntity(), stats));
        selector.addGoal(3, new EatGrassBlockGoal(stats.getEntity(), stats));
    }

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
