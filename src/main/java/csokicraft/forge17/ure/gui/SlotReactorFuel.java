package csokicraft.forge17.ure.gui;

import csokicraft.forge17.ure.recipe.ReactorFuelRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotReactorFuel extends Slot{

	public SlotReactorFuel(IInventory arg0, int arg1, int arg2, int arg3){
		super(arg0, arg1, arg2, arg3);
	}

	@Override
	public boolean isItemValid(ItemStack is){
		return ReactorFuelRecipes.inst.hasRecipe(is);
	}
}
