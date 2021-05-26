package uk.joshiejack.husbandry.data;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.husbandry.block.IncubatorBlock;

@SuppressWarnings("ConstantConditions")
public class HusbandryBlockStates extends BlockStateProvider {
    public HusbandryBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Husbandry.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        model(HusbandryBlocks.FEEDING_TRAY.get());
        model(HusbandryBlocks.BOWL.get());
        model(HusbandryBlocks.NEST.get());
        model(HusbandryBlocks.TRUFFLE_BLOCK.get());
        model(HusbandryBlocks.TROUGH.get());
        ModelFile file = models().getExistingFile(HusbandryBlocks.INCUBATOR.get().getRegistryName());
        VariantBlockStateBuilder builder = getVariantBuilder(HusbandryBlocks.INCUBATOR.get());
        builder.partialState().with(IncubatorBlock.FACING, Direction.EAST).modelForState().modelFile(file).rotationY(90).addModel();
        builder.partialState().with(IncubatorBlock.FACING, Direction.WEST).modelForState().modelFile(file).rotationY(270).addModel();
        builder.partialState().with(IncubatorBlock.FACING, Direction.NORTH).modelForState().modelFile(file).rotationY(0).addModel();
        builder.partialState().with(IncubatorBlock.FACING, Direction.SOUTH).modelForState().modelFile(file).rotationY(180).addModel();
    }

    protected void model(Block block) {
        ModelFile file = models().getExistingFile(block.getRegistryName());
        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(file).build());
    }
}
