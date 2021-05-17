package uk.joshiejack.husbandry.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import uk.joshiejack.husbandry.item.HusbandryItems;

import javax.annotation.Nonnull;


public class DuckEggEntity extends ProjectileItemEntity {
    public DuckEggEntity(EntityType<ProjectileItemEntity> e, World w) {
        super(e, w);
    }

    @Nonnull
    @Override
    protected Item getDefaultItem() {
        return HusbandryItems.DUCK_EGG.get();
    }
}