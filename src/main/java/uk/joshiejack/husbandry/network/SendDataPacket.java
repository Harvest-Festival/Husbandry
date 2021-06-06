package uk.joshiejack.husbandry.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.penguinlib.network.packet.AbstractSyncCompoundNBTPacket;
import uk.joshiejack.penguinlib.util.PenguinLoader;

@PenguinLoader.Packet(NetworkDirection.PLAY_TO_CLIENT)
public class SendDataPacket extends AbstractSyncCompoundNBTPacket {
    private int entityID;

    public SendDataPacket(){}
    public SendDataPacket(int entityID, MobStats<?> stats) {
        super(stats.serializeNBT());
        this.entityID = entityID;
    }

    @Override
    public void encode(PacketBuffer to) {
        to.writeInt(entityID);
        super.encode(to);
    }

    @Override
    public void decode(PacketBuffer from) {
        entityID = from.readInt();
        super.decode(from);
    }

    @Override
    public void handle(PlayerEntity player) {
        Entity entity = player.level.getEntity(entityID);
        if (entity != null) {
            MobStats<?> stats = MobStats.getStats(entity);
            if (stats != null)
                stats.deserializeNBT(tag);
        }
    }
}
