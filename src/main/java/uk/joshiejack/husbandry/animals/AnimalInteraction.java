package uk.joshiejack.husbandry.animals;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;

import static uk.joshiejack.husbandry.Husbandry.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
public class AnimalInteraction {
    public static final ITag.INamedTag<Block> PREVENTS_PASSENGER_DROP = BlockTags.createOptional(new ResourceLocation(MODID, "prevents_passenger_drop"));

    //TODO:
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getPlayer().getPassengers().size() > 0 && event.getHand() == Hand.MAIN_HAND) {
            dismountAnimals(event.getPlayer());
        } else {
            AnimalStats<?> stats = AnimalStats.getStats(event.getTarget());
            if (stats != null) {
                boolean canceled = stats.onRightClick(event.getPlayer(), event.getHand());
                if (canceled) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickGround(PlayerInteractEvent.RightClickBlock event) {
        if (!PREVENTS_PASSENGER_DROP.contains(event.getWorld().getBlockState(event.getPos()).getBlock()))
            dismountAnimals(event.getPlayer());
    }

    @SubscribeEvent //Automatically dismount any entities than are on top of a player
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        //TODO: TEST? dismountAnimals(event.getPlayer());
    }

//    private static void dismountUnvalidated(PlayerEntity player) {
//        player.getPassengers()
//                .forEach(entity -> {
//                    entity.dismountRidingEntity();
//                    entity.rotationPitch = player.rotationPitch;
//                    entity.rotationYaw = player.rotationYaw;
//                    entity.moveRelative(0F, 0.1F, 1.05F, 0.1F);
//                    entity.setEntityInvulnerable(false);
//                });
//    }

    private static void dismountAnimals(PlayerEntity player) {
        player.getPassengers().stream()
                .filter(entity -> AnimalStats.getStats(entity) != null)
                .forEach(entity -> {
                    entity.stopRiding();
                    entity.yRot = player.yRot;
                    entity.xRot = player.xRot;
                    entity.moveRelative(0F, new Vector3d(0.1F, 1.05F, 0.1F));
                    entity.setInvulnerable(false);
                });
    }
}
