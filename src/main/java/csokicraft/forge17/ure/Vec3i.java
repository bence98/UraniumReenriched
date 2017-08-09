package csokicraft.forge17.ure;

import javax.vecmath.Vector3d;

import csokicraft.util.mcforge.UtilMcForge;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class Vec3i {
	public int x, y, z;
	
	public Vec3i(int i, int j, int k){
		x=i;
		y=j;
		z=k;
	}

	public Block getBlock(IBlockAccess xs){
		return xs.getBlock(x, y, z);
	}
	
	public int getBlockMeta(IBlockAccess xs){
		return xs.getBlockMetadata(x, y, z);
	}
	
	public TileEntity getTE(IBlockAccess xs){
		return xs.getTileEntity(x, y, z);
	}
	
	@Override
	public String toString(){
		return "<"+x+", "+y+", "+z+">";
	}
	
	public Vec3i neighborAt(ForgeDirection dir){
		Vector3d u=new Vector3d(x, y, z),
			 v=UtilMcForge.getPosAtSide(u, dir);
		
		return new Vec3i((int)v.x, (int)v.y, (int)v.z);
	}
	
	public double distanceFrom(Vec3i v){
		int dX=x-v.x,
			dY=y-v.y,
			dZ=z-v.z;
		return Math.hypot(dX, Math.hypot(dZ, dY));
	}
}
