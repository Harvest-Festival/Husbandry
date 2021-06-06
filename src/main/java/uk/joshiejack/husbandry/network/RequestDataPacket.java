package uk.joshiejack.husbandry.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.network.PenguinPacket;
import uk.joshiejack.penguinlib.util.PenguinLoader;

@PenguinLoader.Packet(NetworkDirection.PLAY_TO_SERVER)
public class RequestDataPacket extends PenguinPacket {
    private int entityID;

    public RequestDataPacket(){}
    public RequestDataPacket(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void encode(PacketBuffer to) {
        to.writeInt(entityID);
    }

    @Override
    public void decode(PacketBuffer from) {
        entityID = from.readInt();
    }

    @Override
    public void handle(PlayerEntity player) {
        Entity entity = player.level.getEntity(entityID);
        if (entity != null) {
            MobStats<?> stats = MobStats.getStats(entity);
            if (stats != null)
                PenguinNetwork.sendToClient(new SendDataPacket(entityID, stats), (ServerPlayerEntity) player);
        }
    }
}
