package uk.joshiejack.husbandry.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import uk.joshiejack.husbandry.tileentity.TroughTileEntity;
import uk.joshiejack.penguinlib.block.base.AbstractPenguinBlock;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class TroughBlock extends AbstractPenguinBlock {
    private static final VoxelShape COLLISION = Block.box(1D, 0D, 1D, 15D, 24D, 15D);
    private static final VoxelShape SHAPE = Block.box(1D, 0D, 1D, 15D, 12D, 15D);

    public TroughBlock() {
        super(AbstractBlock.Properties.of(Material.WOOD).strength(0.8F).sound(SoundType.WOOD));
        hasInventory = true;
    }

    @Nonnull
    @Override
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos, @Nonnull ISelectionContext ctx) {
        return COLLISION;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos, @Nonnull ISelectionContext ctx) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(@Nonnull BlockState state, @Nonnull IBlockReader world) {
        return new TroughTileEntity();
    }
}
