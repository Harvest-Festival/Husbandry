package uk.joshiejack.husbandry.animals.traits.lifestyle;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AbstractAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IDataTrait;
import uk.joshiejack.husbandry.animals.traits.types.IInteractiveTrait;
import uk.joshiejack.husbandry.animals.traits.types.IJoinWorldTrait;

import java.util.Objects;

public class LameableTrait extends AbstractAnimalTrait implements IJoinWorldTrait, IInteractiveTrait, IDataTrait {
    private boolean lamed;

    public LameableTrait(String name) {
        super(name);
    }

    @Override
    public void onJoinWorld(AnimalStats<?> stats) {
        if (lamed)
           lame(stats.getEntity());
    }

    @Override
    public boolean onRightClick(AnimalStats<?> stats, PlayerEntity player, Hand hand) {
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
