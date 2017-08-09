package csokicraft.forge17.ure.item;

import csokicraft.forge17.ure.UraniumRE;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabURE extends CreativeTabs{

	public CreativeTabURE(){
		super("UraniumRE");
	}

	@Override
	public Item getTabIconItem(){
		return UraniumRE.itemNuclear;
	}

}
