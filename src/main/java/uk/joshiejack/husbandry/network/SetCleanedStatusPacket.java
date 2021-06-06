package uk.joshiejack.husbandry.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import uk.joshiejack.husbandry.api.trait.TraitType;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.happiness.CleanableTrait;
import uk.joshiejack.penguinlib.network.PenguinPacket;
import uk.joshiejack.penguinlib.util.PenguinLoader;

@PenguinLoader.Packet(NetworkDirection.PLAY_TO_CLIENT)
public class SetCleanedStatusPacket extends PenguinPacket {
    private int entityID;
    private boolean cleaned;

    public SetCleanedStatusPacket() {}
    public SetCleanedStatusPacket(int entityID, boolean cleaned) {
        this.entityID = entityID;
        this.cleaned = cleaned;
    }

    @Override
    public void encode(PacketBuffer to) {
        to.writeInt(entityID);
        to.writeBoolean(cleaned);
    }

    @Override
    public void decode(PacketBuffer from) {
        entityID = from.readInt();
        cleaned = from.readBoolean();
    }

    @Override
    public void handle(PlayerEntity player) {
        Entity entity = player.level.getEntity(entityID);
        if (entity != null) {
            MobStats<?> stats = MobStats.getStats(entity);
            if (stats != null)
                stats.getTraits(TraitType.DATA)
                        .filter(s -> s instanceof CleanableTrait)
                        .findFirst()
                        .ifPresent(t -> ((CleanableTrait) t).setCleaned(stats, cleaned));
        }
    }
}