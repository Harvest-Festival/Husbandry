package uk.joshiejack.husbandry.entity.traits.happiness;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.husbandry.api.trait.IDataTrait;
import uk.joshiejack.husbandry.api.trait.IIconTrait;
import uk.joshiejack.husbandry.api.trait.IInteractiveTrait;
import uk.joshiejack.husbandry.api.trait.INewDayTrait;
import uk.joshiejack.husbandry.item.HusbandryItems;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;

public class TreatableTrait implements IDataTrait, IInteractiveTrait, INewDayTrait, IIconTrait {
    public static final ITag.INamedTag<Item> TREATS = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "treat"));
    private boolean treated; //If the mob has been treated
    private int genericTreatsGiven;
    private int speciesTreatsGiven;
    private boolean annoyed; //If the player has insulted the mob today
    private Icon icon;


    @OnlyIn(Dist.CLIENT)
    @Override
    public Icon getIcon(IMobStats<?> stats) {
        if (icon == null)
            icon = new ItemIcon(stats.getSpecies().getTreat());
        return treated ? icon : icon.shadowed();
    }

    @Override
    public void onNewDay(IMobStats<?> stats) {
        if (stats.getHappinessModifier() > 1 && genericTreatsGiven >= stats.getSpecies().getGenericTreats()
                && speciesTreatsGiven >= stats.getSpecies().getSpeciesTreats()) {
            genericTreatsGiven -= stats.getSpecies().getGenericTreats();
            speciesTreatsGiven -= stats.getSpecies().getSpeciesTreats();
            stats.setHappinessModifier(stats.getHappinessModifier() - 1);
        }

        treated = false;
        annoyed = false;
    }

    @Override
    public boolean onRightClick(IMobStats<?> stats, PlayerEntity player, Hand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (!TREATS.contains(held.getItem())) return false;
        boolean generic = held.getItem() == HusbandryItems.GENERIC_TREAT.get();
        if (!generic) { //Feeding the wrong treat will upset them!
            if (stats.getSpecies().getTreat() != held.getItem() && !annoyed) {
                annoyed = true;
                stats.decreaseHappiness(Husbandry.HusbandryConfig.wrongTreatLoss.get());
                held.shrink(1);
                return true;
            }
        }

        //Feeding the correct treat makes them happy
        //But only if they haven't been fed already today
        if (!treated) {
            if (generic) {
                genericTreatsGiven++;
            } else speciesTreatsGiven++;

            held.shrink(1); //Remove it
            stats.increaseHappiness(generic ? Husbandry.HusbandryConfig.typeTreatGain.get() : Husbandry.HusbandryConfig.genericTreatGain.get());
            treated = true;
            return true;
        }

        return false;
    }

    @Override
    public void load(CompoundNBT nbt) {
        treated = nbt.getBoolean("Treated");
        genericTreatsGiven = nbt.getInt("GenericTreats");
        speciesTreatsGiven = nbt.getInt("TypeTreats");
        annoyed = nbt.getBoolean("Annoyed");
    }

    @Override
    public void save(CompoundNBT nbt) {
        nbt.putBoolean("Treated", treated);
        nbt.putBoolean("Annoyed", annoyed);
        nbt.putInt("GenericTreats", genericTreatsGiven);
        nbt.putInt("TypeTreats", speciesTreatsGiven);
    }
}