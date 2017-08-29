package csokicraft.forge17.ure.block;

import csokicraft.forge17.ure.UraniumRE;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ReactorLeakHandler{
	public static final int LEAK_RADIUS=7, LEAK_MAX_ELEVATION=3, LEAK_INTENSITY=100, LEAK_PASSES=5;
	
	public static boolean leak(int x, int y, int z, World w){
		if(w.isAirBlock(x, y, z)){
			Block b=w.getBlock(x, y-1, z);
			if(b.isSideSolid(w, x, y, z, ForgeDirection.UP)){
				return w.setBlock(x, y, z, UraniumRE.blockGoo);
			}
		}else{
			Block b=w.getBlock(x, y, z);
			int meta=w.getBlockMetadata(x, y, z);
			if((b.equals(Blocks.water)||b.equals(Blocks.flowing_water)&&meta==0)){
				return w.setBlock(x, y, z, UraniumRE.blockD2O);
			}
			if(b.equals(UraniumRE.blockGoo)&&meta<BlockWasteGoo.MAX_META){
				return w.setBlockMetadataWithNotify(x, y, z, meta+1, 3);
			}
		}
		
		return false;
	}
}
