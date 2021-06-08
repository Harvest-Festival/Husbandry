package uk.joshiejack.husbandry.data;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.block.FeedingTrayBlock;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.husbandry.block.IncubatorBlock;
import uk.joshiejack.husbandry.block.TroughBlock;

@SuppressWarnings("ConstantConditions")
public class HusbandryBlockStates extends BlockStateProvider {
    public HusbandryBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Husbandry.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        model(HusbandryBlocks.BOWL.get());
        model(HusbandryBlocks.NEST.get());
        model(HusbandryBlocks.TRUFFLE_BLOCK.get());
        ModelFile file = models().getExistingFile(HusbandryBlocks.INCUBATOR.get().getRegistryName());
        VariantBlockStateBuilder builder = getVariantBuilder(HusbandryBlocks.INCUBATOR.get());
        builder.partialState().with(IncubatorBlock.FACING, Direction.EAST).modelForState().modelFile(file).rotationY(90).addModel();
        builder.partialState().with(IncubatorBlock.FACING, Direction.WEST).modelForState().modelFile(file).rotationY(270).addModel();
        builder.partialState().with(IncubatorBlock.FACING, Direction.NORTH).modelForState().modelFile(file).rotationY(0).addModel();
        builder.partialState().with(IncubatorBlock.FACING, Direction.SOUTH).modelForState().modelFile(file).rotationY(180).addModel();
        trough(HusbandryBlocks.TROUGH.get());
        feedingtray(HusbandryBlocks.FEEDING_TRAY.get());
    }

    private void feedingtray(Block block) {
        getMultipartBuilder(block)
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "feeding_tray"))).addModel().end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "bird_feed_semi")))
                .addModel().condition(FeedingTrayBlock.FILL, 1).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "bird_feed_full")))
                .addModel().condition(FeedingTrayBlock.FILL, 2).end();
    }

    private void trough(Block block) {
        getMultipartBuilder(block)
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "trough"))).addModel().end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "hay_one_quarter")))
                .addModel().condition(TroughBlock.TYPE, TroughBlock.FoodType.HAY).condition(TroughBlock.FILL, 1).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "hay_two_quarters")))
                .addModel().condition(TroughBlock.TYPE, TroughBlock.FoodType.HAY).condition(TroughBlock.FILL, 2).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "hay_three_quarters")))
                .addModel().condition(TroughBlock.TYPE, TroughBlock.FoodType.HAY).condition(TroughBlock.FILL, 3).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "hay_four_quarters")))
                .addModel().condition(TroughBlock.TYPE, TroughBlock.FoodType.HAY).condition(TroughBlock.FILL, 4).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "slop_one_quarter")))
                .addModel().condition(TroughBlock.TYPE, TroughBlock.FoodType.SLOP).condition(TroughBlock.FILL, 1).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "slop_two_quarters")))
                .addModel().condition(TroughBlock.TYPE, TroughBlock.FoodType.SLOP).condition(TroughBlock.FILL, 2).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "slop_three_quarters")))
                .addModel().condition(TroughBlock.TYPE, TroughBlock.FoodType.SLOP).condition(TroughBlock.FILL, 3).end()
                .part().modelFile(models().getExistingFile(new ResourceLocation(Husbandry.MODID, "slop_four_quarters")))
                .addModel().condition(TroughBlock.TYPE, TroughBlock.FoodType.SLOP).condition(TroughBlock.FILL, 4).end();
    }

    protected void model(Block block) {
        ModelFile file = models().getExistingFile(block.getRegistryName());
        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(file).build());
    }
}
