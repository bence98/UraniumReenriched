package csokicraft.forge17.ure.item;

import java.util.List;

import csokicraft.forge17.ure.entity.EntityPlasmaCell;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/** All the cells. metas:
  * 0:empty
  * 1:uranium
  * 2:depleted
  * 3:deuterium
  * 4:plasma
  * 5:steam*/
public class ItemCell extends Item{
	IIcon icons[]=new IIcon[6];
	
	public ItemCell(){
		hasSubtypes=true;
	}
	
	@Override
	public void registerIcons(IIconRegister reg){
		for(int i=0;i<icons.length;i++)
			icons[i]=reg.registerIcon("UraniumRE:cell_"+getName(i));
	}
	
	protected String getName(int i){
		switch(i){
		case 0:
			return "empty";
		case 1:
			return "uranium";
		case 2:
			return "depleted";
		case 3:
			return "deut";
		case 4:
			return "plasma";
		case 5:
			return "steam";
		}
		return null;
	}

	@Override
	public IIcon getIconFromDamage(int i){
		return icons[i];
	}
	
	@Override
	public String getUnlocalizedName(ItemStack is){
		return "item.urecell."+getName(is.getItemDamage());
	}
	
	@Override
	public void getSubItems(Item arg0, CreativeTabs arg1, List l){
		for(int x=0;x<icons.length;x++) l.add(new ItemStack(arg0, 1, x));
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer p){
		if(getName(is.getItemDamage()).equals("plasma")){
			if(!p.capabilities.isCreativeMode){
				is.stackSize--;
			}
			w.spawnEntityInWorld(new EntityPlasmaCell(w, p));
			w.playSoundAtEntity(p, "random.bow", 0.5f, 0.5f);
			return is;
		}
		return super.onItemRightClick(is, w, p);
	}
}
