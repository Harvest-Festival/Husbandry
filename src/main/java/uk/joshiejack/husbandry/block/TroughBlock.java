package uk.joshiejack.husbandry.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.items.IItemHandler;
import uk.joshiejack.husbandry.tileentity.TroughTileEntity;
import uk.joshiejack.penguinlib.block.base.AbstractPenguinBlock;

import javax.annotation.Nonnull;
import java.util.Locale;

@SuppressWarnings("deprecation")
public class TroughBlock extends AbstractPenguinBlock {
    private static final VoxelShape COLLISION = Block.box(1D, 0D, 1D, 15D, 24D, 15D);
    private static final VoxelShape SHAPE = Block.box(1D, 0D, 1D, 15D, 12D, 15D);
    public static final EnumProperty<FoodType> TYPE = EnumProperty.create("type", FoodType.class);
    public static final IntegerProperty FILL = IntegerProperty.create("fill", 0, 4);

    public TroughBlock() {
        super(AbstractBlock.Properties.of(Material.WOOD).strength(0.8F).sound(SoundType.WOOD));
        registerDefaultState(defaultBlockState().setValue(FILL, 0).setValue(TYPE, FoodType.HAY));
        setHasInventory();
    }

    @Override
    protected int getInsertAmount(IItemHandler handler, ItemStack held) {
        return 1;
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

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FILL, TYPE);
    }

    public enum FoodType implements IStringSerializable {
        HAY, SLOP;

        @Nonnull
        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
