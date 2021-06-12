package uk.joshiejack.husbandry.api;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import uk.joshiejack.penguinlib.util.icon.Icon;

import javax.annotation.Nullable;
import java.util.List;

public interface IProducts {
    Icon getIcon();

    int getDaysBetweenProducts();

    List<ItemStack> getProduct(MobEntity entity, @Nullable PlayerEntity player);
}
