package uk.joshiejack.husbandry.entity.stats;

import com.google.common.collect.Lists;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import uk.joshiejack.husbandry.api.IProducts;
import uk.joshiejack.penguinlib.util.helpers.minecraft.FakePlayerHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class Products implements IProducts {
    public static final Products NONE = new Products(Integer.MAX_VALUE, null);
    private static final List<ItemStack> EMPTY = Lists.newArrayList(ItemStack.EMPTY);
    private final ResourceLocation lootTable;
    private final int dayBetween;

    public Products(int daysBetween, ResourceLocation lootTable){
        this.dayBetween = daysBetween;
        this.lootTable = lootTable;
    }

    @Override
    public int getDaysBetweenProducts() {
        return dayBetween;
    }

    /** Size will be 0, 1 or 2 **/
    @Override
    public List<ItemStack> getProduct(LivingEntity entity, @Nullable PlayerEntity player) {
        if (lootTable == null)
            return EMPTY;
        LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld)entity.level))
                .withParameter(LootParameters.ORIGIN, entity.position())
                .withParameter(LootParameters.THIS_ENTITY, entity)
                .withRandom(entity.getRandom());
        if (player != null) lootcontext$builder.withParameter(LootParameters.KILLER_ENTITY, player);
        else lootcontext$builder.withParameter(LootParameters.KILLER_ENTITY, FakePlayerHelper.getFakePlayerWithPosition((ServerWorld) entity.level, entity.blockPosition()));
        LootTable loottable = Objects.requireNonNull(entity.level.getServer()).getLootTables().get(lootTable);
        return loottable.getRandomItems(lootcontext$builder.create(LootParameterSets.CHEST));
    }
}
