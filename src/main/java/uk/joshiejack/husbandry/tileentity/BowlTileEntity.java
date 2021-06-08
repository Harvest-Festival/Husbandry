package uk.joshiejack.husbandry.tileentity;

import net.minecraft.item.ItemStack;
import uk.joshiejack.husbandry.block.BowlBlock;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.husbandry.entity.traits.food.EatsCatFoodTrait;
import uk.joshiejack.husbandry.entity.traits.food.EatsDogFoodTrait;
import uk.joshiejack.husbandry.entity.traits.food.EatsRabbitFoodTrait;

import javax.annotation.Nonnull;

@SuppressWarnings("ConstantConditions")
public class BowlTileEntity extends AbstractFoodSupplyTileEntity {
    public BowlTileEntity() {
        super(HusbandryTileEntities.BOWL.get());
    }

    @Override
    public boolean canPlaceItem(int slot, @Nonnull ItemStack stack) {
        return EatsRabbitFoodTrait.RABBIT_FOOD.contains(stack.getItem()) || EatsDogFoodTrait.DOG_FOOD.contains(stack.getItem()) || EatsCatFoodTrait.CAT_FOOD.contains(stack.getItem());
    }

    private BowlBlock.FoodType getFoodTypeFromStack(ItemStack stack) {
        return EatsRabbitFoodTrait.RABBIT_FOOD.contains(stack.getItem()) ? BowlBlock.FoodType.RABBIT_FOOD
                : EatsCatFoodTrait.CAT_FOOD.contains(stack.getItem()) ? BowlBlock.FoodType.CAT_FOOD : BowlBlock.FoodType.DOG_FOOD;
    }

    @Override
    public void setItem(int slot, @Nonnull ItemStack stack) {
        super.setItem(slot, stack);
        if (stack.isEmpty())
            level.setBlock(worldPosition, HusbandryBlocks.BOWL.get().defaultBlockState().setValue(BowlBlock.FILL, 0), 2);
        else level.setBlock(worldPosition, HusbandryBlocks.BOWL.get().defaultBlockState()
                .setValue(BowlBlock.TYPE, getFoodTypeFromStack(stack))
                .setValue(BowlBlock.FILL, stack.getCount()), 2);
    }
}
