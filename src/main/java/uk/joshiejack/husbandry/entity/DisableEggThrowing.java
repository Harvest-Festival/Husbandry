package uk.joshiejack.husbandry.entity;

import net.minecraft.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.joshiejack.husbandry.Husbandry;

@Mod.EventBusSubscriber(modid = Husbandry.MODID)
public class DisableEggThrowing {
    @SubscribeEvent
    public static void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getItemStack().getItem() == Items.EGG)
            event.setCanceled(true);
    }
}
