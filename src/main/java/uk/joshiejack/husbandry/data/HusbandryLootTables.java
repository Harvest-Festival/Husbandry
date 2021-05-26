package uk.joshiejack.husbandry.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.husbandry.item.HusbandryItems;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class HusbandryLootTables extends LootTableProvider {
    public HusbandryLootTables(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationTracker validationtracker) {
        map.forEach((name, table) -> LootTableManager.validate(validationtracker, name, table));
    }

    @Nonnull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(Pair.of(Blocks::new, LootParameterSets.BLOCK), Pair.of(Chests::new, LootParameterSets.FISHING), Pair.of(Entities::new, LootParameterSets.ENTITY));
    }

    public static class Entities extends EntityLootTables {
        @Nonnull
        @Override
        protected Iterable<EntityType<?>> getKnownEntities() {
            return ForgeRegistries.ENTITIES.getValues().stream()
                    .filter((block) -> Husbandry.MODID.equals(Objects.requireNonNull(block.getRegistryName()).getNamespace()))
                    .collect(Collectors.toList());
        }

        protected CompoundNBT color(DyeColor color) {
            CompoundNBT tag = new CompoundNBT();
            tag.putByte("Color", (byte) color.getId());
            return tag;
        }

        @Override
        protected void addTables() {
            addProducts("milk", LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(Items.MILK_BUCKET))));
            addProducts("truffle", LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(HusbandryBlocks.TRUFFLE_BLOCK.get()))));
            addProducts("chicken_egg", LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(Items.EGG))));
            addProducts("rabbit_foot", LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(Items.RABBIT_FOOT).when(RandomChance.randomChance(0.1F)))));
            addProducts("wool", LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                    .add(ItemLootEntry.lootTableItem(Items.WHITE_WOOL).apply(SetCount.setCount(RandomValueRange.between(5F, 11F)))
                            .when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NBTPredicate(color(DyeColor.WHITE))))))
                    .add(ItemLootEntry.lootTableItem(Items.ORANGE_WOOL).apply(SetCount.setCount(RandomValueRange.between(5F, 11F)))
                            .when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NBTPredicate(color(DyeColor.ORANGE))))))
                    .add(ItemLootEntry.lootTableItem(Items.MAGENTA_WOOL).apply(SetCount.setCount(RandomValueRange.between(5F, 11F)))
                            .when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NBTPredicate(color(DyeColor.MAGENTA))))))
                    .add(ItemLootEntry.lootTableItem(Items.LIGHT_BLUE_WOOL).apply(SetCount.setCount(RandomValueRange.between(5F, 11F)))
                            .when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NBTPredicate(color(DyeColor.LIGHT_BLUE))))))
                    .add(ItemLootEntry.lootTableItem(Items.YELLOW_WOOL).apply(SetCount.setCount(RandomValueRange.between(5F, 11F)))
                            .when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NBTPredicate(color(DyeColor.YELLOW))))))
                    .add(ItemLootEntry.lootTableItem(Items.LIME_WOOL).apply(SetCount.setCount(RandomValueRange.between(5F, 11F)))
                            .when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NBTPredicate(color(DyeColor.LIME))))))
                    .add(ItemLootEntry.lootTableItem(Items.PINK_WOOL).apply(SetCount.setCount(RandomValueRange.between(5F, 11F)))
                            .when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NBTPredicate(color(DyeColor.PINK))))))
                    .add(ItemLootEntry.lootTableItem(Items.GRAY_WOOL).apply(SetCount.setCount(RandomValueRange.between(5F, 11F)))
                            .when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NBTPredicate(color(DyeColor.GRAY))))))
                    .add(ItemLootEntry.lootTableItem(Items.LIGHT_GRAY_WOOL).apply(SetCount.setCount(RandomValueRange.between(5F, 11F)))
                            .when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NBTPredicate(color(DyeColor.LIGHT_GRAY))))))
                    .add(ItemLootEntry.lootTableItem(Items.CYAN_WOOL).apply(SetCount.setCount(RandomValueRange.between(5F, 11F)))
                            .when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NBTPredicate(color(DyeColor.CYAN))))))
                    .add(ItemLootEntry.lootTableItem(Items.PURPLE_WOOL).apply(SetCount.setCount(RandomValueRange.between(5F, 11F)))
                            .when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NBTPredicate(color(DyeColor.PURPLE))))))
                    .add(ItemLootEntry.lootTableItem(Items.BLUE_WOOL).apply(SetCount.setCount(RandomValueRange.between(5F, 11F)))
                            .when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NBTPredicate(color(DyeColor.BLUE))))))
                    .add(ItemLootEntry.lootTableItem(Items.BROWN_WOOL).apply(SetCount.setCount(RandomValueRange.between(5F, 11F)))
                            .when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NBTPredicate(color(DyeColor.BROWN))))))
                    .add(ItemLootEntry.lootTableItem(Items.GREEN_WOOL).apply(SetCount.setCount(RandomValueRange.between(5F, 11F)))
                            .when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NBTPredicate(color(DyeColor.GREEN))))))
                    .add(ItemLootEntry.lootTableItem(Items.RED_WOOL).apply(SetCount.setCount(RandomValueRange.between(5F, 11F)))
                            .when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NBTPredicate(color(DyeColor.RED))))))
                    .add(ItemLootEntry.lootTableItem(Items.BLACK_WOOL).apply(SetCount.setCount(RandomValueRange.between(5F, 11F)))
                            .when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NBTPredicate(color(DyeColor.BLACK))))))
            ));
        }

        protected void addProducts(String type, LootTable.Builder builder) {
            ResourceLocation lootTable = new ResourceLocation(Husbandry.MODID, "entities/products/" + type);
            super.add(lootTable, builder);
        }
    }

    public static class Blocks extends BlockLootTables {
        @Nonnull
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ForgeRegistries.BLOCKS.getValues().stream()
                    .filter((block) -> Husbandry.MODID.equals(Objects.requireNonNull(block.getRegistryName()).getNamespace()))
                    .collect(Collectors.toList());
        }

        @Override
        protected void addTables() {
            dropSelf(HusbandryBlocks.FEEDING_TRAY.get());
            dropSelf(HusbandryBlocks.BOWL.get());
            dropSelf(HusbandryBlocks.NEST.get());
            dropSelf(HusbandryBlocks.INCUBATOR.get());
            dropSelf(HusbandryBlocks.TROUGH.get());
            add(HusbandryBlocks.TRUFFLE_BLOCK.get(), (block) -> createSilkTouchDispatchTable(block, applyExplosionDecay(block, ItemLootEntry.lootTableItem(HusbandryItems.TRUFFLE.get())
                    .apply(SetCount.setCount(RandomValueRange.between(1.0F, 3.0F)))
                    .apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        }
    }

    public static class Chests extends ChestLootTables {
        @Override
        public void accept(@Nonnull BiConsumer<ResourceLocation, LootTable.Builder> builder) {
            /* TODO:
             *  @SubscribeEvent
    public static void onDungeonLootLoad(LootTableLoadEvent event) {
        if (HusbandryConfig.enableLootTreats) {
            if (event.getName().equals(new ResourceLocation("minecraft:chests/simple_dungeon"))) {
                LootPool pool = event.getTable().getPool("main");
                for (ItemTreat.Treat treat: ItemTreat.Treat.values()) {
                    if (treat != ItemTreat.Treat.GENERIC) {
                        pool.addEntry(getEntry("husbandry_treat_" + treat.name().toLowerCase(Locale.ENGLISH), treat, 2, 3));
                    } else pool.addEntry(getEntry("husbandry_treat_" + treat.name().toLowerCase(Locale.ENGLISH), treat, 3, 7));
                }
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static LootEntryItem getEntry(String name, ItemTreat.Treat treat, int min, int max) {
        LootCondition[] conditions = new LootCondition[0];
        return new LootEntryItem(HusbandryItems.TREAT, 8, 0,
                new LootFunction[]{ new SetMetadata(conditions, new RandomValueRange(treat.ordinal())),
                new SetCount(conditions, new RandomValueRange(min, max))}, conditions, name);
    }
             */
        }
    }
}
