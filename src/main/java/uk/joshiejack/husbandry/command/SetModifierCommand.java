package uk.joshiejack.husbandry.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.MobEntity;
import org.apache.commons.lang3.mutable.MutableBoolean;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.network.SetModifierPacket;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

public class SetModifierCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("modifier")
                .then(Commands.argument("entity", EntityArgument.entities())
                .then(Commands.argument("amount", IntegerArgumentType.integer(1, 5))
                .executes(ctx -> {
                    MutableBoolean success = new MutableBoolean(false);
                    EntityArgument.getEntities(ctx, "entity").forEach(entity -> {
                        if (!(entity instanceof MobEntity)) return;
                        int amount = IntegerArgumentType.getInteger(ctx, "amount");
                        MobStats<?> stats = MobStats.getStats(entity);
                        if (stats == null) return;
                        stats.setHappinessModifier(amount);
                        PenguinNetwork.sendToNearby(new SetModifierPacket(entity.getId(), amount), entity);
                        success.setTrue();
                    });

                    return success.isTrue() ? 1 : 0;
                })));
    }
}
