package uk.joshiejack.husbandry.entity.traits.lifestyle;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Lazy;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.HusbandryAPI;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IDataTrait;
import uk.joshiejack.husbandry.api.trait.IIconTrait;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;
import uk.joshiejack.husbandry.api.trait.INewDayTrait;
import uk.joshiejack.husbandry.item.HusbandryItems;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;

public class MammalTrait implements IDataTrait, IInteractiveTrait, INewDayTrait, IIconTrait {
    public static final Lazy<Icon> ICON = Lazy.of(() -> new ItemIcon(HusbandryItems.MIRACLE_POTION.get()));
    public static final ITag.INamedTag<Item> IMPREGNATES_MAMMALS = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "impregnates_mammals"));
    private int gestation;//How many days this mob has been pregnant
    private boolean pregnant; //If the mob is pregnant

    @OnlyIn(Dist.CLIENT)
    @Override
    public Icon getIcon(IMobStats<?> stats) {
        return pregnant ? ICON.get() : ItemIcon.EMPTY;
    }

    @Override
    public void onNewDay(IMobStats<?> stats) {
        if (pregnant) {
            gestation--;
            if (gestation <= 0) {
                pregnant = false;
                giveBirth(stats);
            }
        }
    }

    @Override
    public boolean onRightClick(IMobStats<?> stats, PlayerEntity player, Hand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (IMPREGNATES_MAMMALS.contains(held.getItem())
                && !pregnant && stats.getSpecies().getDaysToBirth() != 0) {
            pregnant = true;
            gestation = stats.getSpecies().getDaysToBirth();
            held.shrink(1);
            return true;
        }

        return false;
    }

    public boolean isPregnant() {
        return pregnant;
    }

    private void giveBirth(IMobStats<?> stats) {
        MobEntity entity = stats.getEntity();
        stats.increaseHappiness(100); //Happy to have a child
        int chance = entity.level.random.nextInt(100);
        int offspring = chance >= 99 ? 3 : chance >= 90 ? 2 : 1;
        for (int i = 0; i < offspring; i++) {
            if (entity instanceof AgeableEntity) {
                AgeableEntity ageable = ((AgeableEntity) entity).getBreedOffspring((ServerWorld) entity.level, (AgeableEntity) entity);
                if (ageable != null) {
                    ageable.setAge(-Integer.MAX_VALUE);
                    ageable.setPos(entity.xo, entity.yo, entity.zo);
                    IMobStats<?> babyStats = HusbandryAPI.instance.getStatsForEntity(ageable);
                    if (babyStats != null)
                        babyStats.increaseHappiness(stats.getHappiness() / 2);
                    entity.level.addFreshEntity(ageable);
                }
            } else
                entity.getType().spawn((ServerWorld) entity.level, null, null, null, entity.blockPosition(), SpawnReason.BREEDING, true, true);
        }
    }

    @Override
    public void save(CompoundNBT tag) {
        tag.putInt("Gestation", gestation);
        tag.putBoolean("Pregnant", pregnant);
    }

    @Override
    public void load(CompoundNBT nbt) {
        gestation = nbt.getInt("Gestation");
        pregnant = nbt.getBoolean("Pregnant");
    }
}
