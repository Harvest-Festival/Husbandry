package uk.joshiejack.husbandry.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import uk.joshiejack.husbandry.tileentity.NestTileEntity;
import uk.joshiejack.penguinlib.block.base.AbstractPenguinBlock;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class NestBlock extends AbstractPenguinBlock {
    private static final VoxelShape COLLISION = VoxelShapes.box(0.15D, 0D, 0.15D, 0.85D, 0.2D, 0.85D);
    private static final VoxelShape SHAPE = VoxelShapes.box(0.15D, 0D, 0.15D, 0.85D, 0.35D, 0.85D);

    public NestBlock() {
        super(AbstractBlock.Properties.of(Material.WOOD).strength(0.5F).sound(SoundType.WOOD));
        setHasInventory();
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
        return new NestTileEntity();
    }
}
