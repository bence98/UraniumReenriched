package csokicraft.forge17.ure.gui;

import csokicraft.forge17.ure.recipe.RecyclingRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotRecycle extends Slot {

	public SlotRecycle(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack is){
		return RecyclingRecipes.inst.hasRecipe(is);
	}
}
