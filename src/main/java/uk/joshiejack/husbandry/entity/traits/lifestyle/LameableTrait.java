package uk.joshiejack.husbandry.entity.traits.lifestyle;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IDataTrait;
import uk.joshiejack.husbandry.api.trait.IIconTrait;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;
import uk.joshiejack.husbandry.api.trait.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.traits.happiness.AbstractLoveableTrait;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;
import uk.joshiejack.penguinlib.util.icon.TextureIcon;

import java.util.Objects;

public class LameableTrait implements IIconTrait, IJoinWorldTrait, IInteractiveTrait, IDataTrait {
    public static final Icon ICON = new TextureIcon(AbstractLoveableTrait.HUSBANDRY_ICONS, 0, 0);
    private boolean lamed;

    @OnlyIn(Dist.CLIENT)
    @Override
    public Icon getIcon(IMobStats<?> stats) {
        return lamed ? ICON : ItemIcon.EMPTY;
    }

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
        entity.setPersistenceRequired();
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
