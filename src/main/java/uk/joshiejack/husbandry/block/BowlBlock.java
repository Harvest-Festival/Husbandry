package uk.joshiejack.husbandry.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.EnumProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import uk.joshiejack.husbandry.tileentity.BowlTileEntity;
import uk.joshiejack.penguinlib.block.base.AbstractPenguinBlock;

import javax.annotation.Nonnull;
import java.util.Locale;

@SuppressWarnings("deprecation")
public class BowlBlock extends AbstractPenguinBlock {
    public static final EnumProperty<Fill> FILL = EnumProperty.create("fill", Fill.class);
    private static final VoxelShape BOWL_COLLISSION = VoxelShapes.box(0.15D, 0D, 0.15D, 0.85D, 0.2D, 0.85D);
    private static final VoxelShape BOWL_SHAPE = VoxelShapes.box(0.15D, 0D, 0.15D, 0.85D, 0.35D, 0.85D);

    public BowlBlock() {
        super(AbstractBlock.Properties.of(Material.WOOD).strength(0.3F).sound(SoundType.WOOD));
        hasInventory = true;
    }

    @Nonnull
    @Override
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos, @Nonnull ISelectionContext ctx) {
        return BOWL_COLLISSION;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos, @Nonnull ISelectionContext ctx) {
        return BOWL_SHAPE;
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(@Nonnull BlockState state, @Nonnull IBlockReader world) {
        return new BowlTileEntity();
    }

    public enum Fill implements IStringSerializable {
        EMPTY, CAT_FOOD, DOG_FOOD, RABBIT_FOOD;

        @Override
        public @Nonnull String getSerializedName() {
            return name().toLowerCase(Locale.ENGLISH);
        }
    }
}
