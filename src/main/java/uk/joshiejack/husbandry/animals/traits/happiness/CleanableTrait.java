package uk.joshiejack.husbandry.animals.traits.happiness;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IInteractiveTrait;
import uk.joshiejack.husbandry.animals.traits.types.IDataTrait;
import uk.joshiejack.husbandry.animals.traits.types.INewDayTrait;
import uk.joshiejack.husbandry.network.SetCleanedStatusPacket;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.util.PenguinLoader;
import uk.joshiejack.penguinlib.util.helpers.generic.MathsHelper;

@PenguinLoader("cleanable")
public class CleanableTrait extends AnimalTrait implements IDataTrait, IInteractiveTrait, INewDayTrait {
    private int cleanliness;
    private boolean cleaned;

    public CleanableTrait(String name) {
        super(name);
    }

    @Override
    public void onNewDay(AnimalStats<?> stats) {
        setCleaned(stats, false);
        cleanliness = MathsHelper.constrainToRangeInt(cleanliness - 10, -100, 100);
        if (cleanliness <= 0) {
            stats.decreaseHappiness(1); //We dirty, so we no happy
        }
    }

    @Override
    public boolean onRightClick(AnimalStats<?> stats, PlayerEntity player, Hand hand) {
        return false;
    }


    public boolean isClean() {
        return cleaned;
    }

    public boolean clean(AnimalStats<?> stats) {
        if (!cleaned) {
            cleanliness++;
            if (cleanliness == 100) {
                setCleaned(stats, true);
            }
        }

        return cleaned;
    }

    public void setCleaned(AnimalStats<?> stats, boolean cleaned) {
        AgeableEntity entity = stats.getEntity();
        if (entity.level.isClientSide) this.cleaned = cleaned;
        else {
            this.cleaned = cleaned;
            if (cleaned) {
                stats.increaseHappiness(30);
            }

            PenguinNetwork.sendToNearby(new SetCleanedStatusPacket(entity.getId(), cleaned), entity);
        }
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("Cleanliness", cleanliness);
        tag.putBoolean("Cleaned", cleaned);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        cleanliness = nbt.getInt("Cleanliness");
        cleaned = nbt.getBoolean("Cleaned");
    }
}
