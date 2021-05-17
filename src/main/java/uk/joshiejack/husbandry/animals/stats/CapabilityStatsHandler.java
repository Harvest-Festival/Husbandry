package uk.joshiejack.husbandry.animals.stats;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.animals.AnimalSpecies;

@Mod.EventBusSubscriber(modid = Husbandry.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityStatsHandler {
    @SuppressWarnings("CanBeFinal")
    @CapabilityInject(AnimalStats.class)
    public static Capability<AnimalStats<?>> ANIMAL_STATS_CAPABILITY = null;

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(AnimalStats.class, new DefaultAnimalStats<>(), () -> new AnimalStats<>(null, AnimalSpecies.NONE));
    }

    private static class DefaultAnimalStats<T extends AnimalStats<?>> implements Capability.IStorage<T> {
        @Override
        public INBT writeNBT(Capability<T> capability, T instance, Direction side)  {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt) {
            instance.deserializeNBT((CompoundNBT) nbt);
        }
    }
}
