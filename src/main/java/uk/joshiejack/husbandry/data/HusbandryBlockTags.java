package uk.joshiejack.husbandry.data;

import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.entity.MobEventsHandler;

import javax.annotation.Nullable;

public class HusbandryBlockTags extends BlockTagsProvider {
    public HusbandryBlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Husbandry.MODID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags() {
        tag(MobEventsHandler.PREVENTS_PASSENGER_DROP).addTags(BlockTags.BUTTONS, BlockTags.DOORS, BlockTags.FENCE_GATES, Tags.Blocks.CHESTS).add(Blocks.LEVER);
    }
}
