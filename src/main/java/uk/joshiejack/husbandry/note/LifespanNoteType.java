package uk.joshiejack.husbandry.note;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.entity.stats.Species;
import uk.joshiejack.penguinlib.note.Note;
import uk.joshiejack.penguinlib.note.type.NoteType;

import javax.annotation.Nonnull;
import java.util.List;

public class LifespanNoteType extends NoteType {
    private ITextComponent component;
    public LifespanNoteType() {
        super("lifespan");
    }

    @Nonnull
    public ITextComponent getText(Note note) {
        if (component != null) return component;
        component = note.getText();
        List<ITextComponent> list = component.getSiblings();
        Species.TYPES.forEach((type, species) -> {
            list.add(new StringTextComponent("\n ")); //newline before
            list.add(new TranslationTextComponent("note.type.husbandry.lifespan", type.getDescription(),
                    (species.getMinAge() / Husbandry.HusbandryConfig.daysPerYear.get()),
                    (species.getMaxAge() / Husbandry.HusbandryConfig.daysPerYear.get())));
        });

        return component;
    }
}