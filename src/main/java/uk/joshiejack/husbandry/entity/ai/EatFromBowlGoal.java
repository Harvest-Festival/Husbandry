package uk.joshiejack.husbandry.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.tileentity.BowlTileEntity;

import javax.annotation.Nonnull;

public class EatFromBowlGoal extends AbstractMoveToBlockGoal {
    private final ITag.INamedTag<Item> food;

    public EatFromBowlGoal(CreatureEntity entity, AnimalStats<?> stats, ITag.INamedTag<Item> food) {
        super(entity, stats, Orientation.BESIDE, 8);
        this.food = food;
    }

    @Override
    public boolean canUse() {
        return !stats.hasEaten() && entity.getRandom().nextInt(5) == 0 && super.canUse();
    }

    @Override
    protected boolean isValidTarget(@Nonnull IWorldReader world, @Nonnull BlockPos pos) {
        TileEntity tile = world.getBlockEntity(pos);
        if (tile instanceof BowlTileEntity) {
            ItemStack contents = ((BowlTileEntity)tile).getItem(0);
            return contents.getCount() > 0 && food.contains(contents.getItem());
        } else return false;
    }

    @Override
    public void tick() {
        super.tick();
        entity.getLookControl().setLookAt((double) blockPos.getX() + 0.5D, blockPos.getY() + 1,
                (double) blockPos.getZ() + 0.5D, 10.0F, (float) entity.getMaxHeadXRot());

        if (isNearDestination()) {
            TileEntity tile = entity.level.getBlockEntity(blockPos);
            if (tile instanceof BowlTileEntity) {
                ((BowlTileEntity) tile).consume();
                stats.feed();
                entity.playAmbientSound();
                tryTicks = 9999;
            }
        }
    }
}
