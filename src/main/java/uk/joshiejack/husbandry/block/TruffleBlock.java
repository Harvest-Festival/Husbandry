package uk.joshiejack.husbandry.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import uk.joshiejack.penguinlib.block.base.AbstractPenguinBlock;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class TruffleBlock extends AbstractPenguinBlock {
    private static final VoxelShape SHAPE = Block.box(3D, 0D, 3D, 13D, 10D, 13D);

    public TruffleBlock() {
        super(AbstractBlock.Properties.of(Material.CAKE).strength(0.3F).sound(SoundType.FUNGUS));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos, @Nonnull ISelectionContext ctx) {
        return SHAPE;
    }
}
