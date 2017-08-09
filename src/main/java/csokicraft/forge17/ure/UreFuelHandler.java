package csokicraft.forge17.ure;

import net.minecraft.item.ItemStack;

public class UreFuelHandler implements cpw.mods.fml.common.IFuelHandler{
	@Override
	public int getBurnTime(ItemStack arg0){
		if(arg0.getItem().equals(UraniumRE.itemNuclear)&&arg0.getItemDamage()==2)
			return 2000;
		return -1;
	}
}
