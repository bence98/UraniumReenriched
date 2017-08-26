package csokicraft.forge17.ure.recipe;

import net.minecraft.item.ItemStack;

public class ReactorFuelRecipeItemStack extends ReactorFuelRecipeAbstract{
	protected ItemStack in;
	
	public ReactorFuelRecipeItemStack(ItemStack stack, int pow, int wst, ItemStack remain, int t){
		super(pow, wst, remain, t);
		in=stack;
	}
	
	public ItemStack getInput(){
		return in;
	}
	
	public boolean isApplicable(ItemStack is){
		if(is==null) return false;
		return in.isItemEqual(is);
	}
}