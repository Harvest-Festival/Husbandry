package uk.joshiejack.husbandry.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.MobEntity;
import org.apache.commons.lang3.mutable.MutableBoolean;
import uk.joshiejack.husbandry.entity.stats.MobStats;

public class SetHeartsCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("hearts")
                .then(Commands.argument("entity", EntityArgument.entities())
                .then(Commands.argument("amount", IntegerArgumentType.integer(0, 10))
                .executes(ctx -> {
                    MutableBoolean success = new MutableBoolean(false);
                    EntityArgument.getEntities(ctx, "entity").forEach(entity -> {
                        if (!(entity instanceof MobEntity)) return;
                        int hearts = IntegerArgumentType.getInteger(ctx, "amount");
                        MobStats<?> stats = MobStats.getStats(entity);
                        if (stats == null) return;
                        stats.setHearts(hearts);
                        success.setTrue();
                    });

                    return success.isTrue() ? 1 : 0;
                })));
    }
}
