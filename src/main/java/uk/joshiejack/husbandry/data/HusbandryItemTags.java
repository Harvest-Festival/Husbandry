package uk.joshiejack.husbandry.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.husbandry.entity.traits.food.*;
import uk.joshiejack.husbandry.entity.traits.lifestyle.MammalTrait;
import uk.joshiejack.husbandry.item.HusbandryItems;
import uk.joshiejack.penguinlib.util.PenguinTags;

import javax.annotation.Nullable;

public class HusbandryItemTags extends ItemTagsProvider {
    public static final ITag.INamedTag<Item> BUTTER = PenguinTags.forgeTag("butter");
    public static final ITag.INamedTag<Item> MOB_PRODUCT = ItemTags.createOptional(new ResourceLocation(Husbandry.MODID, "mob_product"));

    public HusbandryItemTags(DataGenerator generator, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, blockTagProvider, Husbandry.MODID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTags() {
        tag(BUTTER).add(HusbandryItems.BUTTER.get());
        tag(MobStats.TREATS)
                .add(HusbandryItems.GENERIC_TREAT.get(), HusbandryItems.CAT_TREAT.get(), HusbandryItems.CHICKEN_TREAT.get()
                        , HusbandryItems.COW_TREAT.get(), HusbandryItems.DOG_TREAT.get(), HusbandryItems.HORSE_TREAT.get()
                        , HusbandryItems.PIG_TREAT.get(), HusbandryItems.RABBIT_TREAT.get(), HusbandryItems.SHEEP_TREAT.get()
                        , HusbandryItems.PARROT_TREAT.get(), HusbandryItems.LLAMA_TREAT.get());
        tag(EatsBirdFeedTrait.BIRD_FEED).add(Items.WHEAT_SEEDS, HusbandryItems.BIRD_FEED.get());
        tag(EatsSlopTrait.SLOP).add(Items.CARROT, HusbandryItems.SLOP.get());
        tag(MammalTrait.IMPREGNATES_MAMMALS).add(HusbandryItems.MIRACLE_POTION.get());
        tag(EatsGrassTrait.HAY).add(Items.WHEAT, Items.SEAGRASS, HusbandryItems.FODDER.get());
        tag(PenguinTags.RAW_FISHES).add(Items.COD); //Only because the tag below complains else
        tag(EatsCatFoodTrait.CAT_FOOD).add(HusbandryItems.CAT_FOOD.get()).addTag(PenguinTags.RAW_FISHES);
        tag(EatsDogFoodTrait.DOG_FOOD).add(HusbandryItems.DOG_FOOD.get(), Items.RABBIT, Items.BEEF, Items.PORKCHOP, Items.MUTTON, Items.CHICKEN);
        tag(EatsRabbitFoodTrait.RABBIT_FOOD).add(HusbandryItems.RABBIT_FOOD.get(), Items.CARROT);
        tag(HusbandryItemTags.MOB_PRODUCT).add(Items.FEATHER, Items.CHICKEN, Items.PORKCHOP, Items.BEEF, Items.LEATHER,
                Items.RABBIT, Items.RABBIT_FOOT, Items.RABBIT_HIDE, Items.MUTTON).addTags(ItemTags.WOOL);
        tag(PenguinTags.SICKLES).add(HusbandryItems.SICKLE.get());
    }
}
