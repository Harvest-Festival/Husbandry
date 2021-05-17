package uk.joshiejack.husbandry.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.HusbandrySounds;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.happiness.CleanableTrait;

import javax.annotation.Nonnull;

public class BrushItem extends Item {
    public BrushItem() {
        super(new Item.Properties().stacksTo(1).durability(64).tab(Husbandry.TAB));
    }

    @Override
    public ActionResultType interactLivingEntity(@Nonnull ItemStack stack, PlayerEntity player, @Nonnull LivingEntity target, @Nonnull Hand hand) {
        AnimalStats<?> stats = AnimalStats.getStats(target);
        World world = player.level;
        if (stats != null) {
            if (stats.hasTrait("cleanable") && ((CleanableTrait)stats.getTrait("cleanable")).clean(stats)) {
                if (world.isClientSide) {
                    for (int j = 0; j < 30D; j++) {
                        double d7 = (target.xo - 0.5D) + world.random.nextFloat();
                        double d8 = (target.yo - 0.5D) + world.random.nextFloat();
                        double d9 = (target.zo - 0.5D) + world.random.nextFloat();
                        world.addParticle(ParticleTypes.HAPPY_VILLAGER, d8, 1.0D + d7 - 0.125D, d9, 0, 0, 0);
                    }
                }

                world.playSound(player, player.xo, player.yo, player.zo, HusbandrySounds.BRUSH.get(), SoundCategory.PLAYERS, 1.5F, 1F);
                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.PASS;
    }
 }
