package uk.joshiejack.husbandry.tileentity;

import net.minecraft.item.ItemStack;
import uk.joshiejack.husbandry.animals.traits.food.EatsCatFoodTrait;
import uk.joshiejack.husbandry.animals.traits.food.EatsDogFoodTrait;
import uk.joshiejack.husbandry.animals.traits.food.EatsRabbitFoodTrait;
import uk.joshiejack.husbandry.block.BowlBlock;
import uk.joshiejack.husbandry.block.HusbandryBlocks;

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

    @Override
    public void setItem(int slot, @Nonnull ItemStack stack) {
        super.setItem(slot, stack);
        if (EatsRabbitFoodTrait.RABBIT_FOOD.contains(stack.getItem()))
            level.setBlock(worldPosition, HusbandryBlocks.BOWL.get().defaultBlockState().setValue(BowlBlock.FILL, BowlBlock.Fill.RABBIT_FOOD), 2);
        else if (EatsCatFoodTrait.CAT_FOOD.contains(stack.getItem()))
            level.setBlock(worldPosition, HusbandryBlocks.BOWL.get().defaultBlockState().setValue(BowlBlock.FILL, BowlBlock.Fill.CAT_FOOD), 2);
        else if (EatsDogFoodTrait.DOG_FOOD.contains(stack.getItem()))
            level.setBlock(worldPosition, HusbandryBlocks.BOWL.get().defaultBlockState().setValue(BowlBlock.FILL, BowlBlock.Fill.CAT_FOOD), 2);
        else if (stack.isEmpty())
            level.setBlock(worldPosition, HusbandryBlocks.BOWL.get().defaultBlockState().setValue(BowlBlock.FILL, BowlBlock.Fill.EMPTY), 2);
    }
}
