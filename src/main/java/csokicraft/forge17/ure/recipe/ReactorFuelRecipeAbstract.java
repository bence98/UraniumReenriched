package csokicraft.forge17.ure.recipe;

import net.minecraft.item.ItemStack;

public abstract class ReactorFuelRecipeAbstract implements IReactorFuelRecipe{
	protected int power, waste, tier;
	protected ItemStack out;

	public ReactorFuelRecipeAbstract(int pow, int wst, ItemStack remain, int t){
		power=pow;
		waste=wst;
		out=remain;
		tier=t;
	}
	
	public ItemStack getOutput(){
		return out;
	}
	
	public int getPower(){
		return power;
	}
	
	public int getWaste(){
		return waste;
	}
	
	public int getTier(){
		return tier;
	}
}