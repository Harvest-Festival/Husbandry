package uk.joshiejack.husbandry.entity.traits.types;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import uk.joshiejack.husbandry.entity.stats.MobStats;

public interface IInteractiveTrait extends IMobTrait {
    boolean onRightClick(MobStats<?> stats, PlayerEntity player, Hand hand);
}
