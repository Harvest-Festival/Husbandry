package uk.joshiejack.husbandry.api;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public interface IProducts {
    int getDaysBetweenProducts();

    List<ItemStack> getProduct(MobEntity entity, @Nullable PlayerEntity player);
}
