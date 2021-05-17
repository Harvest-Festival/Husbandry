package uk.joshiejack.husbandry.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import uk.joshiejack.penguinlib.network.PenguinPacket;
import uk.joshiejack.penguinlib.util.PenguinLoader;

@PenguinLoader.Packet(NetworkDirection.PLAY_TO_CLIENT)
public class SpawnHeartsPacket extends PenguinPacket {
    private int entityID;
    private boolean positive;

    public SpawnHeartsPacket(){}
    public SpawnHeartsPacket(int entityID, boolean positive) {
        this.entityID = entityID;
        this.positive = positive;
    }

    @Override
    public void encode(PacketBuffer to) {
        to.writeInt(entityID);
        to.writeBoolean(positive);
    }

    @Override
    public void decode(PacketBuffer from) {
        entityID = from.readInt();
        positive = from.readBoolean();
    }

    @Override
    public void handle(PlayerEntity player) {
        World world = player.level;
        Entity entity = world.getEntity(entityID);
        if (entity != null) {
            BasicParticleType type = positive ? ParticleTypes.HEART : ParticleTypes.DAMAGE_INDICATOR;
            int times = positive ? 3 : 16;
            double offset = positive ? -0.125D : 0D;
            for (int j = 0; j < times; j++) {
                double x = (entity.xo - 0.5D) + world.random.nextFloat();
                double y = (entity.yo - 0.5D) + world.random.nextFloat();
                double z = (entity.zo - 0.5D) + world.random.nextFloat();
                world.addParticle(type, x, 1D + y + offset, z, 0, 0, 0);
            }
        }
    }
}
