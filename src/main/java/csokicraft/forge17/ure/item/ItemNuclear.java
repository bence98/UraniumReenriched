package csokicraft.forge17.ure.item;

import java.util.List;

import csokicraft.forge17.ure.UraniumRE;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/** Generic radiating items. metas:
  * 0=uranium ingot
  * 1=radiating iron
  * 2=carbon-14
  * 3=plutonium ingot */
public class ItemNuclear extends Item{
	IIcon icons[]=new IIcon[4];
	
	public ItemNuclear(){
		hasSubtypes=true;
	}
	
	@Override
	public void registerIcons(IIconRegister reg){
		for(int i=0;i<icons.length;i++)
			icons[i]=reg.registerIcon("UraniumRE:"+getName(i));
	}

	@Override
	public IIcon getIconFromDamage(int i){
		return icons[i];
	}

	@Override
	public String getUnlocalizedName(ItemStack is){
		return "item.urecmp."+getName(is.getItemDamage());
	}
	
	protected String getName(int i){
		switch(i){
		case 0:
			return "ur_i";
		case 1:
			return "rad_fe";
		case 2:
			return "c14";
		case 3:
			return "pu_i";
		}
		return null;
	}
	
	@Override
	public void getSubItems(Item it, CreativeTabs tab, List l){
		for(int x=0;x<icons.length;x++) l.add(new ItemStack(it, 1, x));
	}
}
