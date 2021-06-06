package uk.joshiejack.husbandry.plugins.kubejs;

import dev.latvian.kubejs.entity.EntityJS;
import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.kubejs.util.UtilsJS;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import uk.joshiejack.husbandry.api.IMobStats;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class IMobStatsJS {
    private final IMobStats<?> stats;

    public IMobStatsJS(IMobStats<?> stats) {
        this.stats = stats;
    }

    public EntityJS getEntity() {
        return UtilsJS.getWorld(stats.getEntity().level).getEntity(stats.getEntity());
    }

    /* Product data */
    public int getDaysBetweenProducts() {
        return stats.getSpecies().getProducts().getDaysBetweenProducts();
    }

    public List<ItemStackJS> getProduct(@Nullable PlayerEntity player) {
        return stats.getSpecies().getProducts().getProduct(stats.getEntity(), player).stream().map(ItemStackJS::of).collect(Collectors.toList());
    }

    /* Species data */
    public int getMinAge() {
        return stats.getSpecies().getMinAge();
    }

    public int getMaxAge() {
        return stats.getSpecies().getMaxAge();
    }

    public int getGenericTreats() {
        return stats.getSpecies().getGenericTreats();
    }

    public int getSpeciesTreats() {
        return stats.getSpecies().getSpeciesTreats();
    }

    public int getDaysToBirth() {
        return stats.getSpecies().getDaysToBirth();
    }

    public int getDaysToMaturity() {
        return stats.getSpecies().getDaysToMaturity();
    }

    public Item getTreat() {
        return stats.getSpecies().getTreat();
    }

    /* Mob Stats */
    public int getHappiness() {
        return stats.getHappiness();
    }

    public void decreaseHappiness(int happiness) {
        stats.decreaseHappiness(happiness);
    }

    public void increaseHappiness(int happiness) {
        stats.increaseHappiness(happiness);
    }

    public void feed() {
        stats.feed();
    }

    public int getHunger() {
        return stats.getHunger();
    }

    public boolean isUnloved() {
        return stats.isUnloved();
    }

    public boolean setLoved() {
        return stats.setLoved();
    }

    public boolean canProduceProduct() {
        return stats.canProduceProduct();
    }

    public void setProduced(int amount) {
        stats.setProduced(amount);
    }

    public boolean isHungry() {
        return stats.isHungry();
    }

    public int getHearts() {
        return stats.getHearts();
    }

    public int getMaxRelationship() {
        return stats.getMaxRelationship();
    }

    public void resetProduct() {
        stats.resetProduct();
    }
}
