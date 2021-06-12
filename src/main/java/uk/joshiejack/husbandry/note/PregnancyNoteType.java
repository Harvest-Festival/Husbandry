package uk.joshiejack.husbandry.note;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import uk.joshiejack.husbandry.entity.stats.Species;
import uk.joshiejack.husbandry.entity.traits.lifestyle.MammalTrait;
import uk.joshiejack.penguinlib.note.Note;
import uk.joshiejack.penguinlib.note.type.NoteType;

import javax.annotation.Nonnull;
import java.util.List;

public class PregnancyNoteType extends NoteType {
    private ITextComponent component;
    public PregnancyNoteType() {
        super("pregnancy");
    }

    @Nonnull
    public ITextComponent getText(Note note) {
        if (component != null) return component;
        component = note.getText();
        List<ITextComponent> list = component.getSiblings();
        Species.TYPES.forEach((type, species) -> {
            if (species.getTraits().stream().anyMatch(t -> t instanceof MammalTrait)) {
                list.add(new StringTextComponent("\n ")); //newline before
                list.add(new TranslationTextComponent("note.type.husbandry.gestation", type.getDescription(), species.getDaysToBirth()));
            }
        });
        return component;
    }
}