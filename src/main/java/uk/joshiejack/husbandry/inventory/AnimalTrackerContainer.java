package uk.joshiejack.husbandry.inventory;

import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.penguinlib.inventory.AbstractBookContainer;

public class AnimalTrackerContainer extends AbstractBookContainer {
    public AnimalTrackerContainer(int windowID) {
        super(Husbandry.BOOK.get(), windowID);
    }
}
