package uk.joshiejack.husbandry.tileentity;

import uk.joshiejack.husbandry.crafting.HusbandryRecipes;
import uk.joshiejack.husbandry.crafting.SpinningWheelRecipe;
import uk.joshiejack.penguinlib.tile.machine.AbstractIRecipeMachine;

@SuppressWarnings("unused")
public class SpinningWheelTileEntity extends AbstractIRecipeMachine<SpinningWheelRecipe> {
    public SpinningWheelTileEntity() {
        super(HusbandryTileEntities.SPINNING_WHEEL.get(), HusbandryRecipes.SPINNING_WHEEL);
    }
}
