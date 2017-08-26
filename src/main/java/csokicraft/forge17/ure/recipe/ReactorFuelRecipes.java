package csokicraft.forge17.ure.recipe;

import java.util.*;

import net.minecraft.item.ItemStack;

public class ReactorFuelRecipes{
	public static ReactorFuelRecipes inst = new ReactorFuelRecipes();
	
	protected List<IReactorFuelRecipe> recipes;
	
	protected ReactorFuelRecipes(){
		recipes = new ArrayList<>();
	}

	public void registerRecipe(ItemStack in, int power, int waste, ItemStack remain, int tier){
		if(hasRecipe(in)) removeRecipe(in);
		recipes.add(new ReactorFuelRecipeItemStack(in, power, waste, remain, tier));
	}

	public void registerRecipe(String ore, int power, int waste, ItemStack remain, int tier){
		recipes.add(new ReactorFuelRecipeOre(ore, power, waste, remain, tier));
	}
	
	public void removeRecipe(ItemStack is){
		recipes.remove(getRecipe(is));
	}

	public boolean hasRecipe(ItemStack is){
		return getRecipe(is) != null;
	}
	
	public IReactorFuelRecipe getRecipe(ItemStack is){
		if(is == null) return null;
		for(IReactorFuelRecipe r:recipes)
			if(r.isApplicable(is)) return r;
		return null;
	}
}
