package uk.joshiejack.husbandry.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.block.HusbandryBlocks;

@SuppressWarnings("ConstantConditions")
public class HusbandryTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Husbandry.MODID);
    public static final RegistryObject<TileEntityType<BowlTileEntity>> BOWL = TILE_ENTITIES.register("bowl", () -> TileEntityType.Builder.of(BowlTileEntity::new, HusbandryBlocks.BOWL.get()).build(null));
    public static final RegistryObject<TileEntityType<FeedingTrayTileEntity>> FEEDING_TRAY = TILE_ENTITIES.register("feeding_tray", () -> TileEntityType.Builder.of(FeedingTrayTileEntity::new, HusbandryBlocks.FEEDING_TRAY.get()).build(null));
    public static final RegistryObject<TileEntityType<IncubatorTileEntity>> INCUBATOR = TILE_ENTITIES.register("incubator", () -> TileEntityType.Builder.of(IncubatorTileEntity::new, HusbandryBlocks.INCUBATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<NestTileEntity>> NEST = TILE_ENTITIES.register("nest", () -> TileEntityType.Builder.of(NestTileEntity::new, HusbandryBlocks.NEST.get()).build(null));
    public static final RegistryObject<TileEntityType<TroughTileEntity>> TROUGH = TILE_ENTITIES.register("trough", () -> TileEntityType.Builder.of(TroughTileEntity::new, HusbandryBlocks.TROUGH.get()).build(null));
}
