package csokicraft.forge17.ure.item;

import java.util.List;

import csokicraft.forge17.ure.UraniumRE;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/** Generic items. metas:
  * 0:radiating gear
  * 1:grinder component
  * 2:coil
  * 3:lead ingot
  * 4:c-14 crystal
  * 5:uranium dust
  * 6:tiny uranium dust
  * 7:lead dust
  * 8:tiny lead dust
  * 9:iron dust
  * 10:tiny iron dust
  * 11:heavy water bucket
  * 12:plutonium dust
  * 13:tiny plutonium dust
  * 14:mutated stick
  * 15:nuclear goo
  * 16: debugger */
public class ItemUreComponent extends Item{
	IIcon icons[]=new IIcon[17];
	private boolean noBucketHandler=false;
	
	public ItemUreComponent(){
		hasSubtypes=true;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack arg0){
		return "item.urecmp."+getName(arg0.getItemDamage());
	}
	
	protected String getName(int i){
		switch(i){
		case 0:
			return "gear";
		case 1:
			return "grndcmp";
		case 2:
			return "coil";
		case 3:
			return "pb_i";
		case 4:
			return "c14cry";
		case 5:
			return "ur_dust";
		case 6:
			return "ur_tnd";
		case 7:
			return "pb_dust";
		case 8:
			return "pb_tnd";
		case 9:
			return "fe_dust";
		case 10:
			return "fe_tnd";
		case 11:
			return "heavybucket";
		case 12:
			return "pu_dust";
		case 13:
			return "pu_tnd";
		case 14:
			return "mut_stick";
		case 15:
			return "nucl_goo";
		case 16:
			return "debugger";
		}
		return null;
	}
	
	@Override
	public IIcon getIconFromDamage(int i){
		return icons[i];
	}
	
	@Override
	public void registerIcons(IIconRegister reg){
		for(int i=0;i<icons.length;i++)
			icons[i]=reg.registerIcon("UraniumRE:"+getName(i));
	}
	
	@Override
	public void getSubItems(Item it, CreativeTabs tab, List l){
		for(int x=0;x<icons.length;x++) l.add(new ItemStack(it, 1, x));
	}
	
	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer p, World w, int x, int y, int z, int s, float f1, float f2, float f3){
		if(noBucketHandler) return false;
		if(is.getItemDamage()!=11) return false;
		switch(ForgeDirection.getOrientation(s)){
		case UP:
			y++;
			break;
		case DOWN:
			y--;
			break;
		case EAST:
			x++;
			break;
		case WEST:
			x--;
			break;
		case NORTH:
			z--;
			break;
		case SOUTH:
			z++;
			break;
		default:
			break;
		}
		w.setBlock(x, y, z, UraniumRE.blockD2O);
		if(p.capabilities.isCreativeMode) return true;
		if(is.stackSize>1){
			is.stackSize--;
			EntityItem e=new EntityItem(w, p.posX, p.posY, p.posZ, new ItemStack(Items.bucket));
			w.spawnEntityInWorld(e);
		}else is.func_150996_a(Items.bucket);
		return true;
	}
}
