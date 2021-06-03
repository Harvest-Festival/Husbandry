package uk.joshiejack.husbandry.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import uk.joshiejack.husbandry.tileentity.IncubatorTileEntity;
import uk.joshiejack.penguinlib.block.base.AbstractRotatableBlock;

import javax.annotation.Nonnull;
import java.util.EnumMap;

@SuppressWarnings("deprecation")
public class IncubatorBlock extends AbstractRotatableBlock {
    private static final EnumMap<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    static {
        SHAPES.put(Direction.NORTH, Block.box(1D, 0D, 3D, 15D, 11D, 13D));
        SHAPES.put(Direction.SOUTH, Block.box(1D, 0D, 3D, 15D, 11D, 13D));
        SHAPES.put(Direction.WEST, Block.box(3D, 0D, 1D, 13D, 11D, 15D));
        SHAPES.put(Direction.EAST, Block.box(3D, 0D, 1D, 13D, 11D, 15D));
    }

    public IncubatorBlock() {
        super(AbstractBlock.Properties.of(Material.WOOD).strength(1.5F).sound(SoundType.WOOD));
        hasInventory = true;
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
