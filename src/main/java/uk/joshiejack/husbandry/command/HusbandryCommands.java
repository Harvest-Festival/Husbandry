package uk.joshiejack.husbandry.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.joshiejack.husbandry.Husbandry;

@Mod.EventBusSubscriber(modid = Husbandry.MODID)
public class HusbandryCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                LiteralArgumentBuilder.<CommandSource>literal(Husbandry.MODID)
                        .requires(cs -> cs.hasPermission(2))
                        .then(SetHeartsCommand.register())
                        .then(SetHappinessCommand.register())

        );
    }
}