package uk.joshiejack.husbandry.data;

import joptsimple.internal.Strings;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.item.HusbandryItems;

import java.util.Objects;

public class HusbandryItemModels extends ItemModelProvider {
    public HusbandryItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Husbandry.MODID, existingFileHelper);
    }

    private boolean isFeed(String path) {
        return path.contains("feed") || path.contains("food") || path.equals("fodder") | path.equals("slop");
    }

    @Override
    protected void registerModels() {
        HusbandryItems.ITEMS.getEntries().stream()
                .map(RegistryObject::get)
                .forEach(item -> {
                    String path = Objects.requireNonNull(item.getRegistryName()).getPath();
                    if (item instanceof BlockItem)
                        getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + path)));
                    else {
                        if (path.contains("spawn_egg")) {
                            withExistingParent(path, mcLoc("item/template_spawn_egg"));
                        } else {
                            String subdir =
                                    item.getFoodProperties() != null ? "food/"
                                            : path.contains("treat") ? "treat/"
                                            : isFeed(path) ? "feed/"
                                            : Strings.EMPTY;
                            singleTexture(path, mcLoc("item/generated"), "layer0", modLoc("item/" + subdir + path.replace("_treat", "")));
                        }
                    }
                });
    }
}
