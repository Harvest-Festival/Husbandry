package uk.joshiejack.husbandry.crafting;

import com.google.gson.JsonObject;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.block.HusbandryBlocks;
import uk.joshiejack.penguinlib.item.crafting.AbstractSimplePenguinRecipe;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;

public class IncubatorRecipe extends AbstractSimplePenguinRecipe<EntityType<?>> {
    private final RangedInteger amount;

    public IncubatorRecipe(ResourceLocation resource, Ingredient ingredient, EntityType<?> entity, RangedInteger amount) {
        super(HusbandryRegistries.INCUBATOR, HusbandryRegistries.INCUBATOR_SERIALIZER.get(), resource, ingredient, entity);
        this.amount = amount;
    }

    public void hatch(ServerWorld world, BlockPos pos, ItemStack stack) {
        for (int i = 0; i < amount.randomValue(world.random); i++) {
            Entity entity = output.create(world);
            if (entity == null) return;
            if (entity instanceof MobEntity)
                ((MobEntity)entity).setBaby(true);
            if (entity instanceof SlimeEntity) {
                try {
                    ObfuscationReflectionHelper.findMethod(SlimeEntity.class, "func_70799_a", int.class, boolean.class).invoke(1, true);
                } catch (IllegalAccessException | InvocationTargetException ignored) {}
            }

            entity.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
            if (entity instanceof AgeableEntity && stack.hasTag() && stack.getTag().contains("HeartLevel")) {
                AnimalStats<?> babyStats = AnimalStats.getStats(entity);
                if (babyStats != null)
                    babyStats.increaseHappiness(stack.getTag().getInt("HeartLevel") / 2);
            }

            world.addFreshEntity(entity);
        }
    }

    @Nonnull
    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(HusbandryBlocks.INCUBATOR.get());
    }

    public EntityType<?> getEntity() {
        return output;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<IncubatorRecipe> {

        @Nonnull
        @Override
        public IncubatorRecipe fromJson(@Nonnull ResourceLocation resource, @Nonnull JsonObject json) {
            Ingredient ingredient;
            if (JSONUtils.isArrayNode(json, "ingredient")) {
                ingredient = Ingredient.fromJson(JSONUtils.getAsJsonArray(json, "ingredient"));
            } else {
                ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "ingredient"));
            }

            String s1 = JSONUtils.getAsString(json, "entity");
            int low = 0;
            int high = 4;
            if (json.has("min amount"))
                low = JSONUtils.getAsInt(json, "min amount");
            if (json.has("max amount"))
                high = JSONUtils.getAsInt(json, "max amount");
            if (json.has("amount")) {
                low = JSONUtils.getAsInt(json, "amount");
                high = low;
            }

            EntityType<?> entity = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(s1));
            return new IncubatorRecipe(resource, ingredient, entity, new RangedInteger(low, high));
        }

        @Nonnull
        @Override
        public IncubatorRecipe fromNetwork(@Nonnull ResourceLocation resource, @Nonnull PacketBuffer buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            EntityType<?> entity = buffer.readRegistryIdSafe(EntityType.class);
            RangedInteger integer = new RangedInteger(buffer.readInt(), buffer.readInt());
            return new IncubatorRecipe(resource, ingredient, entity, integer);
        }

        @Override
        public void toNetwork(@Nonnull PacketBuffer buffer, @Nonnull IncubatorRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeRegistryId(recipe.output);
            buffer.writeInt(recipe.amount.getMinInclusive());
            buffer.writeInt(recipe.amount.getMaxInclusive());
        }
    }
}
