package uk.joshiejack.husbandry.inventory;

import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.penguinlib.inventory.AbstractBookContainer;

public class MobTrackerContainer extends AbstractBookContainer {
    public MobTrackerContainer(int windowID) {
        super(Husbandry.BOOK.get(), windowID);
    }
}
