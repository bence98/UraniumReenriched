package csokicraft.forge17.ure.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import csokicraft.forge17.ure.UraniumRE;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BlockWasteGoo extends BlockSnow{
	public static final int MAX_META = 7;

	public BlockWasteGoo(){
		super();
		setHardness(0.1F);
		setStepSound(soundTypeSnow);
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return UraniumRE.itemComponent;
	}
	
	@Override
	public int damageDropped(int p_149692_1_){
		return 15;
	}
	
	public void onEntityCollidedWithBlock(World w, int x, int y, int z, Entity p){
		p.attackEntityFrom(DamageSource.cactus, 2.0F);
	}
	
	@Override
	public String getUnlocalizedName(){
		return "tile.ure.goo";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg){
		blockIcon=reg.registerIcon("UraniumRE:waste_goo");
	}
	
	@Override
	public String getHarvestTool(int metadata){
		return "shovel";
	}
	
	@Override
	public int getHarvestLevel(int metadata){
		return 2;
	}
}
