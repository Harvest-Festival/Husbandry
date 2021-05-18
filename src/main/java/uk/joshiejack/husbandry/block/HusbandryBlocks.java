package uk.joshiejack.husbandry.block;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.joshiejack.husbandry.Husbandry;


@SuppressWarnings("unused")

public class HusbandryBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Husbandry.MODID);
    public static final RegistryObject<Block> FEEDING_TRAY = BLOCKS.register("feeding_tray", FeedingTrayBlock::new);
    public static final RegistryObject<Block> BOWL = BLOCKS.register("bowl", BowlBlock::new);
    public static final RegistryObject<Block> NEST = BLOCKS.register("nest", NestBlock::new);
    //TODO public static final RegistryObject<Block> TROUGH = BLOCKS.register("trough", TroughBlock::new);
    public static final RegistryObject<Block> INCUBATOR = BLOCKS.register("incubator", IncubatorBlock::new);
}
