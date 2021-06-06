package uk.joshiejack.husbandry.entity.traits.product;

import net.minecraft.nbt.CompoundNBT;
import uk.joshiejack.husbandry.api.trait.IDataTrait;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.network.SetProducedProductPacket;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

public abstract class AbstractMobProductTrait implements IDataTrait {
    protected int productReset; //The counter for the product reset
    protected int productsProduced; //How many produced
    
    public void setProduced(MobStats<?> stats, int amount) {
        if (stats.getEntity().level.isClientSide) {
            this.productsProduced = amount;
        } else {
            productsProduced += amount;
            PenguinNetwork.sendToNearby(new SetProducedProductPacket(stats.getEntity().getId(), productsProduced), stats.getEntity());
        }
    }

    @Override
    public void save(CompoundNBT tag) {
        tag.putInt("ProductReset", productReset);
        tag.putInt("ProductsProduced", productsProduced);
    }

    @Override
    public void load(CompoundNBT nbt) {
        productReset = nbt.getInt("ProductReset");
        productsProduced = nbt.getInt("ProductsProduced");
    }
}
