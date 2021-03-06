package uk.joshiejack.husbandry.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.penguinlib.network.PenguinPacket;

public abstract class AbstractSetValuePacket extends PenguinPacket {
    private int entityID;
    protected int value;

    public AbstractSetValuePacket(){}
    public AbstractSetValuePacket(int entityID, int value) {
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
                handle(stats);
        }
    }

    protected abstract void handle(MobStats<?> stats);
}
