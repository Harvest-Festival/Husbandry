package uk.joshiejack.husbandry.entity.traits.happiness;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Lazy;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IDataTrait;
import uk.joshiejack.husbandry.api.trait.IIconTrait;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;
import uk.joshiejack.husbandry.api.trait.INewDayTrait;
import uk.joshiejack.husbandry.item.HusbandryItems;
import uk.joshiejack.husbandry.network.SetCleanedStatusPacket;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.util.helpers.generic.MathsHelper;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;

public class CleanableTrait implements IDataTrait, IInteractiveTrait, INewDayTrait, IIconTrait {
    private static final Lazy<Icon> ICON = Lazy.of(() -> new ItemIcon(new ItemStack(HusbandryItems.BRUSH.get())));
    private int cleanliness;
    private boolean cleaned;

    @Override
    public void onNewDay(IMobStats<?> stats) {
        setCleaned(stats, false);
        cleanliness = MathsHelper.constrainToRangeInt(cleanliness - 10, -100, 100);
        if (cleanliness <= 0) {
            stats.decreaseHappiness(Husbandry.HusbandryConfig.dirtyHappinessLoss.get()); //We dirty, so we no happy
        }
    }

    @Override
    public Icon getIcon(IMobStats<?> stats) {
        return cleaned ? ICON.get() : ICON.get().shadowed();
    }

    @Override
    public boolean onRightClick(IMobStats<?> stats, PlayerEntity player, Hand hand) {
        if (player.getItemInHand(hand).getItem() == HusbandryItems.BRUSH.get() && clean(stats)) {
            World world = player.level;
            MobEntity entity = stats.getEntity();
            if (world.isClientSide) {
                if (world.getDayTime() % 3 ==0) player.swing(hand);
                for (int j = 0; j < 30D; j++) {
                    double x = (entity.xo - 0.5D) + world.random.nextFloat();
                    double y = (entity.yo - 0.5D) + world.random.nextFloat();
                    double z = (entity.zo - 0.5D) + world.random.nextFloat();
                    world.addParticle(ParticleTypes.CRIT, x, 1D + y, z, 0, 0, 0);
                }
            }

            world.playSound(player, player.xo, player.yo, player.zo, Husbandry.HusbandrySounds.BRUSH.get(), SoundCategory.PLAYERS, 1.5F, player.level.random.nextFloat() * 0.1F + 0.9F);
            return true;
        }

        return false;
    }

    private boolean clean(IMobStats<?> stats) {
        if (!cleaned) {
            cleanliness++;
            if (cleanliness == 50) {
                setCleaned(stats, true);
            }

            return true;
        }

        return false;
    }

    public void setCleaned(IMobStats<?> stats, boolean cleaned) {
        MobEntity entity = stats.getEntity();
        if (entity.level.isClientSide) this.cleaned = cleaned;
        else {
            this.cleaned = cleaned;
            if (cleaned) {
                stats.increaseHappiness(Husbandry.HusbandryConfig.cleanedGain.get());
            }

            PenguinNetwork.sendToNearby(new SetCleanedStatusPacket(entity.getId(), cleaned), entity);
        }
    }

    @Override
    public void load(CompoundNBT nbt) {
        cleanliness = nbt.getInt("Cleanliness");
        cleaned = nbt.getBoolean("Cleaned");
    }

    @Override
    public void save(CompoundNBT tag) {
        tag.putInt("Cleanliness", cleanliness);
        tag.putBoolean("Cleaned", cleaned);
    }
}