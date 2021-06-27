package uk.joshiejack.husbandry.network;

import net.minecraftforge.fml.network.NetworkDirection;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.penguinlib.util.PenguinLoader;

@PenguinLoader.Packet(NetworkDirection.PLAY_TO_CLIENT)
public class SetModifierPacket extends AbstractSetValuePacket {
    public SetModifierPacket() {}
    public SetModifierPacket(int entityID, int value) {
        super(entityID, value);
    }

    @Override
    public void handle(MobStats<?> stats) {
        stats.setHappinessModifier(value);
    }
}