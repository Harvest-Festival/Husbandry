package uk.joshiejack.husbandry.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;
import uk.joshiejack.husbandry.Husbandry;

public class HusbandryLanguage extends LanguageProvider {
    public HusbandryLanguage(DataGenerator gen) {
        super(gen, Husbandry.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.husbandry.creativetab", "Husbandry");
        add("gui.husbandry.book", "Animal Stats");
        ForgeRegistries.ITEMS.getValues()
                .stream().filter(i -> i.getRegistryName().getNamespace().equals(Husbandry.MODID))
                .forEach(item -> add(item, StringUtils.capitalize(item.getRegistryName().getPath().replace("_", " "))));
    }
}
