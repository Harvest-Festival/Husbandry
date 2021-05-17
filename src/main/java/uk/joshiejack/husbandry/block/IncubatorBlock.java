package uk.joshiejack.husbandry.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import uk.joshiejack.husbandry.tileentity.IncubatorTileEntity;
import uk.joshiejack.penguinlib.block.base.AbstractRotatableBlock;

import javax.annotation.Nonnull;
import java.util.EnumMap;

@SuppressWarnings("deprecation")
public class IncubatorBlock extends AbstractRotatableBlock {
    private static final EnumMap<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    static {
        SHAPES.put(Direction.NORTH, VoxelShapes.box(0.05D, 0D, 0.3D, 0.95D, 0.7D, 0.95));
        SHAPES.put(Direction.SOUTH, VoxelShapes.box(0.05D, 0D, 0.05D, 0.95D, 0.7D, 0.7));
        SHAPES.put(Direction.WEST, VoxelShapes.box(0.3D, 0D, 0.05D, 0.95D, 0.7D, 0.95));
        SHAPES.put(Direction.EAST, VoxelShapes.box(0.05D, 0D, 0.05D, 0.7D, 0.7D, 0.95));
    }

    public IncubatorBlock() {
        super(AbstractBlock.Properties.of(Material.WOOD).strength(1.5F).sound(SoundType.WOOD));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos, @Nonnull ISelectionContext ctx) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(@Nonnull BlockState state, @Nonnull IBlockReader world) {
        return new IncubatorTileEntity();
    }
}
