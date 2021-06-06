package uk.joshiejack.husbandry.animals.traits.lifestyle;

import net.minecraft.entity.MobEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AbstractAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IBiHourlyTrait;
import uk.joshiejack.husbandry.animals.traits.types.IDataTrait;
import uk.joshiejack.husbandry.animals.traits.types.IJoinWorldTrait;
import uk.joshiejack.husbandry.entity.ai.ShelterAtNightGoal;

public class DiurnalTrait extends AbstractAnimalTrait implements IBiHourlyTrait, IDataTrait, IJoinWorldTrait {
    private boolean wasOutsideInSun; //If the animal was outside last time

    public DiurnalTrait(String name) {
        super(name);
    }

    @Override
    public void onBihourlyTick(AnimalStats<?> stats) {
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
    public void onJoinWorld(AnimalStats<?> stats) {
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