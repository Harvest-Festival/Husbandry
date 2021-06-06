package uk.joshiejack.husbandry.entity.traits.lifestyle;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IDataTrait;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;
import uk.joshiejack.husbandry.api.trait.IJoinWorldTrait;

import java.util.Objects;

public class LameableTrait implements IJoinWorldTrait, IInteractiveTrait, IDataTrait {
    private boolean lamed;

    @Override
    public void onJoinWorld(IMobStats<?> stats) {
        if (lamed)
           lame(stats.getEntity());
    }

    @Override
    public boolean onRightClick(IMobStats<?> stats, PlayerEntity player, Hand hand) {
        lame(stats.getEntity());
        return lamed = true;
    }

    private void lame(MobEntity entity) {
        Objects.requireNonNull(entity.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(0D);
        entity.targetSelector.disableControlFlag(Goal.Flag.TARGET);
    }

    @Override
    public void load(CompoundNBT nbt) {
        lamed = nbt.getBoolean("Lamed");
    }

    @Override
    public void save(CompoundNBT nbt) {
        nbt.putBoolean("Lamed", lamed);
    }
}
