package csokicraft.forge17.ure.recipe;

import net.minecraft.item.ItemStack;

public interface IReactorFuelRecipe extends INuclearRecipe{
	/** Don't modify the returned stack! Use copy() if needed! */
	public ItemStack getOutput();
	public int getPower();
	public int getWaste();
	public int getTier();
}
