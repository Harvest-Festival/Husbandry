package uk.joshiejack.husbandry.animals.traits.types;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;

public interface IInteractiveTrait extends IAnimalTrait {
    boolean onRightClick(AnimalStats<?> stats, PlayerEntity player, Hand hand);
}
