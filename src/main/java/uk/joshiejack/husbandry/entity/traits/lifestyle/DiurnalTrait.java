package uk.joshiejack.husbandry.entity.traits.lifestyle;

import net.minecraft.entity.MobEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import uk.joshiejack.husbandry.entity.ai.ShelterAtNightGoal;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.AbstractMobTrait;
import uk.joshiejack.husbandry.entity.traits.types.IBiHourlyTrait;
import uk.joshiejack.husbandry.entity.traits.types.IDataTrait;
import uk.joshiejack.husbandry.entity.traits.types.IJoinWorldTrait;

public class DiurnalTrait extends AbstractMobTrait implements IBiHourlyTrait, IDataTrait, IJoinWorldTrait {
    private boolean wasOutsideInSun; //If the mob was outside last time

    public DiurnalTrait(String name) {
        super(name);
    }

    @Override
    public void onBihourlyTick(MobStats<?> stats) {
        MobEntity entity = stats.getEntity();
        World world = entity.level;
        BlockPos pos = entity.blockPosition();
        boolean dayTime = world.isDay();
        boolean isRaining = world.isRaining();
        boolean isOutside = world.canSeeSky(pos);
        boolean isOutsideInSun = !isRaining && isOutside && dayTime && !world.getBiome(pos).shouldSnow(world, pos);
        if (isOutsideInSun && wasOutsideInSun) {
            stats.increaseHappiness(2);
        }

        //Mark the past value
        wasOutsideInSun = isOutsideInSun;
    }

    @Override
    public void onJoinWorld(MobStats<?> stats) {
        stats.getEntity().goalSelector.addGoal(4, new ShelterAtNightGoal(stats.getEntity(), stats));
    }

    @Override
    public void load(CompoundNBT nbt) {
        wasOutsideInSun = nbt.getBoolean("InSun");
    }

    @Override
    public void save(CompoundNBT tag) {
        tag.putBoolean("InSun", wasOutsideInSun);
    }
}