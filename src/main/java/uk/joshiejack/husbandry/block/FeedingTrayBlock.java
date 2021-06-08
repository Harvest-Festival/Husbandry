package uk.joshiejack.husbandry.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.items.IItemHandler;
import uk.joshiejack.husbandry.tileentity.FeedingTrayTileEntity;
import uk.joshiejack.penguinlib.block.base.AbstractPenguinBlock;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class FeedingTrayBlock extends AbstractPenguinBlock {
    public static final IntegerProperty FILL = IntegerProperty.create("fill", 0, 2);
    private static final VoxelShape SHAPE = VoxelShapes.box(0.0D, 0D, 0.0D, 1.0D, 0.075D, 1.0D);

    public FeedingTrayBlock() {
        super(AbstractBlock.Properties.of(Material.WOOD).strength(0.4F).sound(SoundType.WOOD).noCollission());
        registerDefaultState(defaultBlockState().setValue(FILL, 0));
        setHasInventory();
    }

    @Override
    protected int getInsertAmount(IItemHandler handler, ItemStack held) {
        return 1;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos, @Nonnull ISelectionContext ctx) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(@Nonnull BlockState state, @Nonnull IBlockReader world) {
        return new FeedingTrayTileEntity();
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FILL);
    }
}
