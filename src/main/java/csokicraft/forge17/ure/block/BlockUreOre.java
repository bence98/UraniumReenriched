package csokicraft.forge17.ure.block;

import java.util.List;

import csokicraft.forge17.ure.common.IMetadataNamable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/** All ores. metas:
  * 0:uranium
  * 1:lead*/
public class BlockUreOre extends Block implements IMetadataNamable{
	IIcon icons[]=new IIcon[2];

	public BlockUreOre(){
		super(Material.rock);
		setHarvestLevel("pickaxe", 2);
		setHardness(3.5f);
		setResistance(5);
	}

	@Override
	public IIcon getIcon(int s, int meta){
		if(meta>1) return null;
		return icons[meta];
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg){
		icons[0]=reg.registerIcon("UraniumRE:ore_uranium");
		icons[1]=reg.registerIcon("UraniumRE:ore_lead");
	}
	
	@Override
	public void getSubBlocks(Item arg0, CreativeTabs arg1, List l){
		for(int x=0;x<icons.length;x++) l.add(new ItemStack(arg0, 1, x));
	}
	
	@Override
	public String getUnlocalizedName(int meta){
		if(meta==1) return "tile.ureore.lead";
		return "tile.ureore.uranium";
	}
}
