package uk.joshiejack.husbandry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class HusbandrySounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Husbandry.MODID);
    public static final RegistryObject<SoundEvent> BRUSH = createSoundEvent("brush");

    private static RegistryObject<SoundEvent> createSoundEvent(@Nonnull String name) {
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(Husbandry.MODID, name)));
    }
}
