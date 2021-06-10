package uk.joshiejack.husbandry.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.text.WordUtils;
import uk.joshiejack.husbandry.Husbandry;

public class HusbandryLanguage extends LanguageProvider {
    public HusbandryLanguage(DataGenerator gen) {
        super(gen, Husbandry.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.husbandry", "Husbandry");
        add("gui.husbandry.stats", "Mob Stats");
        add("gui.husbandry.notes", "Notes");
        ForgeRegistries.ITEMS.getValues()
                .stream().filter(i -> i.getRegistryName().getNamespace().equals(Husbandry.MODID))
                .forEach(item -> add(item, WordUtils.capitalizeFully(item.getRegistryName().getPath().replace("_", " "))));
        add("note.category.husbandry.care_category", "Animal Care");
        add("note.category.husbandry.utils_category", "Husbandry Utilities");
        //TITLES
        addNoteTitle("lifespan_note", "Lifespans");
        addNoteTitle("lifestyle_note", "Habits");
        addNoteTitle("happiness_note", "Happiness");
        addNoteTitle("cleanliness_note", "Cleanliness");
        addNoteTitle("hunger_note", "Hunger and Feeding");
        addNoteTitle("pregnancy_note", "Pregnancy");
        addNoteTitle("products_note", "Products");
        addNoteTitle("treats_note", "Treating");
        addNoteTitle("sickle_note", "Sickle & Fodder");
        addNoteTitle("bowl_note", "Food Bowl");
        addNoteTitle("trough_note", "Trough");
        addNoteTitle("feeding_tray_note", "Feeding Tray");
        addNoteTitle("nest_note", "Nest");
        addNoteTitle("incubator_note", "Incubator");
        //TEXT
        addNoteText("lifespan_note", "//TODO");
        addNoteText("lifestyle_note", "//TODO");
        addNoteText("happiness_note", "//TODO");
        addNoteText("cleanliness_note", "//TODO");
        addNoteText("hunger_note", "//TODO");
        addNoteText("pregnancy_note", "//TODO");
        addNoteText("products_note", "//TODO");
        addNoteText("treats_note", "//TODO");
        addNoteText("sickle_note", "//TODO");
        addNoteText("bowl_note", "//TODO");
        addNoteText("trough_note", "//TODO");
        addNoteText("feeding_tray_note", "//TODO");
        addNoteText("nest_note", "//TODO");
        addNoteText("incubator_note", "//TODO");
    }

    private void addNoteTitle(String note, String text) {
        add("note.title." + Husbandry.MODID + "." + note, text);
    }

    private void addNoteText(String note, String text) {
        add("note.text." + Husbandry.MODID + "." + note, text);
    }
}
