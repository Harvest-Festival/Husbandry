package uk.joshiejack.husbandry.animals;

import com.google.common.collect.Lists;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class AnimalProducts {
    public static final AnimalProducts NONE = new AnimalProducts(Integer.MAX_VALUE, null);
    private static final List<ItemStack> EMPTY = Lists.newArrayList(ItemStack.EMPTY);
    private final ResourceLocation lootTable;
    private final int dayBetween;

    public AnimalProducts (int daysBetween, ResourceLocation lootTable){
        this.dayBetween = daysBetween;
        this.lootTable = lootTable;
    }

    public int getDaysBetweenProducts() {
        return dayBetween;
    }

    /** Size will be 0, 1 or 2 **/
    public List<ItemStack> getProduct(AgeableEntity entity, @Nullable PlayerEntity player) {
        if (lootTable == null)
            return EMPTY;
        LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld)entity.level))
                .withParameter(LootParameters.ORIGIN, entity.position())
                .withParameter(LootParameters.THIS_ENTITY, entity)
                .withParameter(LootParameters.KILLER_ENTITY, entity)
                .withRandom(entity.getRandom());
        LootTable loottable = Objects.requireNonNull(entity.level.getServer()).getLootTables().get(lootTable);
        return loottable.getRandomItems(lootcontext$builder.create(LootParameterSets.CHEST));
    }
}
