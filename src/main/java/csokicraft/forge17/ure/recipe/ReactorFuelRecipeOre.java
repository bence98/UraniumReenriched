package csokicraft.forge17.ure.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ReactorFuelRecipeOre extends ReactorFuelRecipeAbstract{
	protected String in;
	
	public ReactorFuelRecipeOre(String s, int pow, int wst, ItemStack remain, int t){
		super(pow, wst, remain, t);
		in=s;
	}
	
	public String getInput(){
		return in;
	}

	@Override
	public boolean isApplicable(ItemStack is){
		int oreId=OreDictionary.getOreID(in);
		for(int x:OreDictionary.getOreIDs(is))
			if(x==oreId)
				return true;
		return false;
	}

}
