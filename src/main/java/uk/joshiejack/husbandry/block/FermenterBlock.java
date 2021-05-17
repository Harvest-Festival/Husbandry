package uk.joshiejack.husbandry.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import uk.joshiejack.husbandry.tileentity.FermenterTileEntity;
import uk.joshiejack.penguinlib.block.base.AbstractDoubleBlock;

import javax.annotation.Nonnull;

public class FermenterBlock extends AbstractDoubleBlock {
    public FermenterBlock() {
        super(AbstractBlock.Properties.of(Material.METAL).strength(1.2F).requiresCorrectToolForDrops());
        hasInventory = true;
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(@Nonnull BlockState state, @Nonnull IBlockReader world) {
        return new FermenterTileEntity();
    }
}
