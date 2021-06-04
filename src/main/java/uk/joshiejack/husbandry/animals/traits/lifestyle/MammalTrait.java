package uk.joshiejack.husbandry.animals.traits.lifestyle;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.AnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IDataTrait;
import uk.joshiejack.husbandry.animals.traits.types.IInteractiveTrait;
import uk.joshiejack.husbandry.animals.traits.types.INewDayTrait;

public class MammalTrait extends AnimalTrait implements IDataTrait, IInteractiveTrait, INewDayTrait {
    public static final ITag.INamedTag<Item> IMPREGNATES_MAMMALS = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "impregnates_mammals"));
    private int gestation;//How many days this animal has been pregnant
    private boolean pregnant; //If the animal is pregnant

    public MammalTrait(String name) {
        super(name);
    }

    @Override
    public void onNewDay(AnimalStats<?> stats) {
        if (pregnant) {
            gestation--;
            if (gestation <= 0) {
                pregnant = false;
                giveBirth(stats);
            }
        }
    }

    @Override
    public boolean onRightClick(AnimalStats<?> stats, PlayerEntity player, Hand hand) {
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

    private void giveBirth(AnimalStats<?> stats) {
        AgeableEntity entity = stats.getEntity();
        stats.increaseHappiness(100); //Happy to have a child
        int chance = entity.level.random.nextInt(100);
        int offspring = chance >= 99 ? 3 : chance >= 90 ? 2 : 1;
        for (int i = 0; i < offspring; i++) {
            AgeableEntity ageable = entity.getBreedOffspring((ServerWorld) entity.level, entity);
            if (ageable != null) {
                ageable.setAge(-Integer.MAX_VALUE);
                ageable.setPos(entity.xo, entity.yo, entity.zo);
                AnimalStats<?> babyStats = AnimalStats.getStats(ageable);
                if (babyStats != null)
                    babyStats.increaseHappiness(stats.getHappiness() / 2);
                entity.level.addFreshEntity(ageable);
            }
        }
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("Gestation", gestation);
        tag.putBoolean("Pregnant", pregnant);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        gestation = nbt.getInt("Gestation");
        pregnant = nbt.getBoolean("Pregnant");
    }
}
