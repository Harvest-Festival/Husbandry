package uk.joshiejack.husbandry.tileentity;

import uk.joshiejack.husbandry.crafting.FermenterRecipe;
import uk.joshiejack.husbandry.crafting.HusbandryRecipes;
import uk.joshiejack.penguinlib.tile.machine.AbstractIRecipeMachine;

public class FermenterTileEntity extends AbstractIRecipeMachine<FermenterRecipe> {
    public FermenterTileEntity() {
        super(HusbandryTileEntities.FERMENTER.get(), HusbandryRecipes.FERMENTER, "half_day");
    }
}
