package csokicraft.forge17.ure.block;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockHeavyWater extends BlockLiquid{
	public BlockHeavyWater(){
		super(Material.water);
		setHardness(100);
		setLightOpacity(2);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg){
		blockIcon=reg.registerIcon("uraniumre:heavywater_still");
	}
	
	@Override
	public IIcon getIcon(int side, int meta){
		return blockIcon;
	}
	
	@Override
	public String getUnlocalizedName(){
		return "tile.ureliq.heavywater";
	}
}
