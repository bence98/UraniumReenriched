package csokicraft.forge17.ure.recipe;

import net.minecraft.item.ItemStack;

public interface IReactorFuelRecipe extends INuclearRecipe{
	public ItemStack getOutput();
	public int getPower();
	public int getWaste();
	public int getTier();
}
