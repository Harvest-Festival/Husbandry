package uk.joshiejack.husbandry.entity;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import uk.joshiejack.penguinlib.util.PenguinTags;

import javax.annotation.Nonnull;

public class DuckEntity extends ChickenEntity {
    //public static final ResourceLocation LOOT_TABLE = LootTableList.register(new ResourceLocation(Husbandry.MODID, "entities/duck"));
    private static Ingredient TEMPTATION_ITEMS;

    public DuckEntity(EntityType<? extends DuckEntity> type, World worldIn) {
        super(type, worldIn);
        this.setPathfindingMalus(PathNodeType.WATER, 0.8F);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new SwimGoal(this));
        goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
        goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        goalSelector.addGoal(3, new TemptGoal(this, 1.0D, false, TEMPTATION_ITEMS));
        goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D, 4));
        goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1.0D, 4));
        goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 6.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    //TODO: Duck Sounds
    //@Nullable
    //protected ResourceLocation getLootTable() {
//        return LOOT_TABLE;
//    }

    @Override
    public DuckEntity getBreedOffspring(@Nonnull ServerWorld world, @Nonnull AgeableEntity parent) {
        return (DuckEntity) HusbandryEntities.DUCK.get().create(world);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        if (TEMPTATION_ITEMS == null)
            TEMPTATION_ITEMS = Ingredient.of(PenguinTags.RAW_FISHES);
        return TEMPTATION_ITEMS.test(stack);
    }
}
