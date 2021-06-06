package uk.joshiejack.husbandry.api.trait;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import uk.joshiejack.husbandry.api.IMobStats;

public interface IInteractiveTrait {
    boolean onRightClick(IMobStats<?> stats, PlayerEntity player, Hand hand);
}