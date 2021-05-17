package uk.joshiejack.husbandry.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import uk.joshiejack.husbandry.tileentity.OilMakerTileEntity;
import uk.joshiejack.penguinlib.block.base.AbstractRotatableBlock;

import javax.annotation.Nonnull;

public class OilMakerBlock extends AbstractRotatableBlock {
    public OilMakerBlock() {
        super(Properties.of(Material.WOOD).strength(1.2F));
        hasInventory = true;
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(@Nonnull BlockState state, @Nonnull IBlockReader world) {
        return new OilMakerTileEntity();
    }
}
