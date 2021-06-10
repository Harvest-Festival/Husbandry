package uk.joshiejack.husbandry.api.trait;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.penguinlib.util.icon.Icon;

public interface IIconTrait extends IMobTrait {
    @OnlyIn(Dist.CLIENT)
    Icon getIcon(IMobStats<?> stats);
}