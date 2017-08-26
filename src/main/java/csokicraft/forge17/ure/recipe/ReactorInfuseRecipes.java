package csokicraft.forge17.ure.recipe;

import java.util.ArrayList;
import java.util.List;

import csokicraft.forge17.ure.recipe.ReactorFuelRecipeItemStack;
import net.minecraft.item.ItemStack;

public class ReactorInfuseRecipes{
	public static ReactorInfuseRecipes inst=new ReactorInfuseRecipes();
	
	protected List<ReactorInfuseRecipe> recipes;
	
	protected ReactorInfuseRecipes(){
		recipes = new ArrayList<>();
	}
	
	public void registerRecipe(ItemStack in, int power, ItemStack output, int tier){
		if(hasRecipe(in)) removeRecipe(in);
		recipes.add(new ReactorInfuseRecipe(in, power, output, tier));
	}
	
	public void removeRecipe(ItemStack is){
		recipes.remove(getRecipe(is));
	}

	public boolean hasRecipe(ItemStack is){
		return getRecipe(is) != null;
	}
	
	public ReactorInfuseRecipe getRecipe(ItemStack is){
		if(is == null) return null;
		for(ReactorInfuseRecipe r:recipes)
			if(r.isApplicable(is)) return r;
		return null;
	}
	
	public static class ReactorInfuseRecipe implements INuclearRecipe{
		protected ItemStack in, out;
		protected int power, tier;
		
		public ReactorInfuseRecipe(ItemStack stack, int pow, ItemStack output, int t){
			in=stack;
			power=pow;
			out=output;
			tier=t;
		}
		
		public ItemStack getInput(){
			return in;
		}
		
		public ItemStack getOutput(){
			return out;
		}
		
		public int getPower(){
			return power;
		}
		
		public int getTier(){
			return tier;
		}
		
		public boolean isApplicable(ItemStack is){
			if(is==null) return false;
			return in.isItemEqual(is);
		}
	}
}
