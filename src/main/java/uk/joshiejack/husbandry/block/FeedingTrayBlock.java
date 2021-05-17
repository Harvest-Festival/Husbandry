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
import uk.joshiejack.husbandry.tileentity.FeedingTrayTileEntity;
import uk.joshiejack.penguinlib.block.base.AbstractPenguinBlock;

import javax.annotation.Nonnull;
import java.util.Locale;

@SuppressWarnings("deprecation")
public class FeedingTrayBlock extends AbstractPenguinBlock {
    public static final EnumProperty<Fill> FILL = EnumProperty.create("fill", Fill.class);
    private static final VoxelShape FEEDER_AABB = VoxelShapes.box(0.0D, 0D, 0.0D, 1.0D, 0.075D, 1.0D);

    public FeedingTrayBlock() {
        super(AbstractBlock.Properties.of(Material.WOOD).strength(0.4F).sound(SoundType.WOOD).noCollission());
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos, @Nonnull ISelectionContext ctx) {
        return FEEDER_AABB;
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(@Nonnull BlockState state, @Nonnull IBlockReader world) {
        return new FeedingTrayTileEntity();
    }

    public enum Fill implements IStringSerializable {
        EMPTY, SEMI, FULL;

        @Override
        public @Nonnull
        String getSerializedName() {
            return name().toLowerCase(Locale.ENGLISH);
        }
    }
}
