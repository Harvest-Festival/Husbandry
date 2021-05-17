package uk.joshiejack.husbandry.tileentity;

import uk.joshiejack.husbandry.crafting.HusbandryRecipes;
import uk.joshiejack.husbandry.crafting.OilMakerRecipe;
import uk.joshiejack.penguinlib.tile.machine.AbstractIRecipeMachine;

public class OilMakerTileEntity extends AbstractIRecipeMachine<OilMakerRecipe> {
    public OilMakerTileEntity() {
        super(HusbandryTileEntities.OIL_MAKER.get(), HusbandryRecipes.OIL_MAKER);
    }
}
