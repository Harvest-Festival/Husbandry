package uk.joshiejack.husbandry.api;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public interface IProducts {
    int getDaysBetweenProducts();

    List<ItemStack> getProduct(LivingEntity entity, @Nullable PlayerEntity player);
}
