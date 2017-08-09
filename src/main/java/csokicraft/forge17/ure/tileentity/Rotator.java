package csokicraft.forge17.ure.tileentity;

import csokicraft.forge17.ure.common.*;
import net.minecraft.tileentity.TileEntity;

public abstract class Rotator extends TileEntity{
	protected void rotate(int pow){
		TileEntity bot=worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
		if(bot instanceof IRotatable) ((IRotatable) bot).onRotated(pow);
	}
}
