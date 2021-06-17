package uk.joshiejack.husbandry.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.penguinlib.network.PenguinPacket;
import uk.joshiejack.penguinlib.util.PenguinLoader;

@PenguinLoader.Packet(NetworkDirection.PLAY_TO_CLIENT)
public class SetHappinessPacket extends PenguinPacket {
    private int entityID;
    private int value;

    public SetHappinessPacket(){}
    public SetHappinessPacket(int entityID, int value) {
        this.entityID = entityID;
        this.value = value;
    }

    @Override
    public void encode(PacketBuffer to) {
        to.writeInt(entityID);
        to.writeInt(value);
    }

    @Override
    public void decode(PacketBuffer from) {
        entityID = from.readInt();
        value = from.readInt();
    }

    @Override
    public void handle(PlayerEntity player) {
        Entity entity = player.level.getEntity(entityID);
        if (entity != null) {
            MobStats<?> stats = MobStats.getStats(entity);
            if (stats != null)
                stats.setHappiness(value);
        }
    }
}
