package uk.joshiejack.husbandry.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.ResourceLocation;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.item.HusbandryItems;
import uk.joshiejack.husbandry.tileentity.HusbandryTileEntities;
import uk.joshiejack.penguinlib.data.TimeUnitRegistry;
import uk.joshiejack.penguinlib.data.database.CSVUtils;
import uk.joshiejack.penguinlib.data.generators.AbstractDatabaseProvider;
import uk.joshiejack.penguinlib.data.generators.builders.Trade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HusbandryDatabase extends AbstractDatabaseProvider {
    public HusbandryDatabase(DataGenerator gen) {
        super(gen, Husbandry.MODID);
    }

    @Override
    protected void addDatabaseEntries() {
        addTimeUnitForMachine(HusbandryTileEntities.INCUBATOR.get(), TimeUnitRegistry.Defaults.WEEK.getValue());
        new Trade(VillagerProfession.SHEPHERD, 1, HusbandryItems.GENERIC_TREAT.get()).setOutputAmount(5).build(this);
        new Trade(VillagerProfession.SHEPHERD, 2, HusbandryItems.CAT_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new Trade(VillagerProfession.SHEPHERD, 2, HusbandryItems.CHICKEN_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new Trade(VillagerProfession.SHEPHERD, 2, HusbandryItems.COW_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new Trade(VillagerProfession.SHEPHERD, 2, HusbandryItems.DOG_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new Trade(VillagerProfession.SHEPHERD, 2, HusbandryItems.HORSE_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new Trade(VillagerProfession.SHEPHERD, 2, HusbandryItems.PIG_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new Trade(VillagerProfession.SHEPHERD, 2, HusbandryItems.RABBIT_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new Trade(VillagerProfession.SHEPHERD, 2, HusbandryItems.SHEEP_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new Trade(VillagerProfession.SHEPHERD, 2, HusbandryItems.PARROT_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new Trade(VillagerProfession.SHEPHERD, 2, HusbandryItems.LLAMA_TREAT.get()).setOutputAmount(3).setInputAmount(2).build(this);
        new AnimalType("cow").withLifespan(12, 20).withTreat(HusbandryItems.COW_TREAT.get()).levelUpWith(7, 24).withGestationPeriod(9).withMaturityAt(14).withProducts(1, "milk")
                .assign(EntityType.COW).withTraits("eats_grass", "eats_hay", "cleanable", "pettable", "mammal", "milkable", "more_product").build(this);
        new AnimalType("chicken").withLifespan(3, 10).withTreat(HusbandryItems.CHICKEN_TREAT.get()).levelUpWith(5,26).withGestationPeriod(3).withMaturityAt(3).withProducts(1, "chicken_egg")
                .assign(EntityType.CHICKEN).withTraits("eats_bird_feed", "carriable", "lays_egg", "more_product").build(this);
        new AnimalType("pig").withLifespan(6, 10).withTreat(HusbandryItems.PIG_TREAT.get()).levelUpWith(20, 10).withGestationPeriod(4).withMaturityAt(4).withProducts(1, "truffle")
                .assign(EntityType.PIG).withTraits("eats_slop", "cleanable", "pettable", "finds_product", "mammal", "more_product_chance").build(this);
        new AnimalType("sheep").withLifespan(8,12).withTreat(HusbandryItems.SHEEP_TREAT.get()).levelUpWith(2,29).withGestationPeriod(5).withMaturityAt(4).withProducts(7, "wool")
                .assign(EntityType.SHEEP).withTraits("eats_grass", "eats_hay", "cleanable", "pettable", "mammal", "shearable", "faster_product_reset").build(this);
        new AnimalType("horse").withLifespan(25,30).withTreat(HusbandryItems.HORSE_TREAT.get()).levelUpWith(22,10).withGestationPeriod(24).withMaturityAt(12).assign(EntityType.HORSE)
                .withTraits("eats_grass", "eats_hay", "cleanable", "pettable", "mammal", "pet").build(this);
        new AnimalType("llama").withLifespan(15,25).withTreat(HusbandryItems.SHEEP_TREAT.get()).levelUpWith(7,24).withGestationPeriod(11).withMaturityAt(10).assign(EntityType.LLAMA)
                .withTraits("eats_grass", "eats_hay", "cleanable", "pettable", "mammal", "pet").build(this);
        new AnimalType("rabbit").withLifespan(8,12).withTreat(HusbandryItems.RABBIT_TREAT.get()).levelUpWith(14,14).withGestationPeriod(4).withMaturityAt(3).withProducts(4, "rabbit_foot")
                .withTraits("eats_rabbit_food", "carriable", "drops_product", "mammal").assign(EntityType.RABBIT).build(this);
        new AnimalType("cat").withLifespan(10,20).withTreat(HusbandryItems.CAT_TREAT.get()).levelUpWith(3,24).withGestationPeriod(5).withMaturityAt(7)
                .assign(EntityType.CAT).withTraits("eats_cat_food", "carriable", "pettable", "mammal", "pet").build(this);
        new AnimalType("dog").withLifespan(10,13).withTreat(HusbandryItems.DOG_TREAT.get()).levelUpWith(24,3).withGestationPeriod(5).withMaturityAt(7)
                .assign(EntityType.WOLF).withTraits("eats_dog_food", "cleanable", "pettable", "mammal", "pet").build(this);
        new AnimalType("donkey").withLifespan(25,30).withTreat(HusbandryItems.HORSE_TREAT.get()).levelUpWith(22,11).withGestationPeriod(24).withMaturityAt(12)
                .assign(EntityType.DONKEY).assign(EntityType.MULE).withTraits("eats_grass", "eats_hay", "cleanable", "pettable", "mammal", "pet").build(this);
        new AnimalType("parrot").withLifespan(25,50).withTreat(HusbandryItems.PARROT_TREAT.get()).levelUpWith(3,24).withGestationPeriod(4).withMaturityAt(36)
                .assign(EntityType.PARROT).withTraits("eats_bird_feed", "carriable", "pet").build(this);
    }

    private static class AnimalType {
        public static final ResourceLocation NO_PRODUCTS = new ResourceLocation(Husbandry.MODID, "none");
        private String name;
        private RangedInteger lifespan = new RangedInteger(5, 12);
        private Item treat = HusbandryItems.GENERIC_TREAT.get();
        private int generic = 10, type = 10, gestation = 9, maturity = 10, productsFrequency = 0;
        private ResourceLocation productTable = NO_PRODUCTS;
        private List<EntityType<?>> entities = new ArrayList<>();
        private List<String> traits = new ArrayList<>();

        public AnimalType(String name) {
            this.name = name;
        }

        public AnimalType withTraits(String... traits) {
            this.traits.addAll(Arrays.asList(traits));
            return this;
        }

        public AnimalType assign(EntityType<?> entity) {
            this.entities.add(entity);
            return this;
        }

        public AnimalType withLifespan(int min, int max) {
            this.lifespan = new RangedInteger(min, max);
            return this;
        }

        public AnimalType withTreat(Item item) {
            this.treat = item;
            return this;
        }

        public AnimalType levelUpWith(int generic, int type) {
            this.generic = generic;
            this.type = type;
            return this;
        }

        public AnimalType withGestationPeriod(int gestation) {
            this.gestation = gestation;
            return this;
        }

        public AnimalType withMaturityAt(int maturity) {
            this.maturity = maturity;
            return this;
        }

        public AnimalType withProducts(int productFrequency, String table) {
            this.productsFrequency = productFrequency;
            this.productTable = new ResourceLocation(Husbandry.MODID, "entities/products/" + table);
            return this;
        }

        public void build(HusbandryDatabase database) {
            database.addEntry("animal_species", "Name,Min Age,Max Age,Treat Item,Generic Treats,Species Treats,Days to Birth,Days to Maturity,Product Frequency,Products Loot Table",
                    CSVUtils.join(name, lifespan.getMinInclusive(), lifespan.getMaxInclusive(), treat.getRegistryName(), generic, type, gestation, maturity, productsFrequency, productTable == NO_PRODUCTS ? "none" : productTable.toString()));
            entities.forEach(entity -> database.addEntry("animal_entities", "Entity,Species", CSVUtils.join(Objects.requireNonNull(entity.getRegistryName()).toString(), name)));
            traits.forEach(trait -> database.addEntry("animal_traits", "Species,Trait", CSVUtils.join(name, trait)));
        }
    }
}
