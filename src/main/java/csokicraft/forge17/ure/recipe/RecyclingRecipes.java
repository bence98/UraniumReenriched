package csokicraft.forge17.ure.recipe;

import java.util.*;

import csokicraft.forge17.ure.recipe.ReactorInfuseRecipes.ReactorInfuseRecipe;
import net.minecraft.item.ItemStack;

public class RecyclingRecipes{
	public static RecyclingRecipes inst = new RecyclingRecipes();
	
	protected List<RecyclingRecipe> recipes;
	
	protected RecyclingRecipes(){
		recipes=new ArrayList<>();
	}
	
	public void registerRecipe(ItemStack in, WeightedItemStackResult... out){
		RecyclingRecipe rec=new RecyclingRecipe(in);
		for(WeightedItemStackResult res:out)
			rec.addOutput(res);
		recipes.add(rec);
	}
	
	public void removeRecipe(ItemStack is){
		recipes.remove(getRecipe(is));
	}

	public boolean hasRecipe(ItemStack is){
		return getRecipe(is) != null;
	}
	
	public RecyclingRecipe getRecipe(ItemStack is){
		if(is == null) return null;
		for(RecyclingRecipe r:recipes)
			if(r.isApplicable(is)) return r;
		return null;
	}
	
	public static class RecyclingRecipe implements INuclearRecipe{
		protected ItemStack input;
		protected List<WeightedItemStackResult> outputs;
		
		public RecyclingRecipe(ItemStack is){
			input=is;
			outputs=new LinkedList<>();
		}
		
		public void addOutput(WeightedItemStackResult res){
			outputs.add(res);
		}
		
		public List<WeightedItemStackResult> getOutputs(){
			return Collections.unmodifiableList(outputs);
		}
		
		public boolean isApplicable(ItemStack is){
			if(is==null) return false;
			return input.isItemEqual(is);
		}
	}
	
	public static class WeightedItemStackResult{
		/** stackSize will be multiplied if {@link #amount}>1 */
		protected ItemStack stack;
		protected double amount;
		
		public WeightedItemStackResult(ItemStack is, double d){
			stack=is;
			amount=d;
		}
		
		public ItemStack getTypeStack(){
			return stack;
		}
		
		public double getAmountChance(){
			return amount;
		}
	}
}
