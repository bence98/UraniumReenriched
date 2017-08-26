package csokicraft.forge17.ure.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import csokicraft.forge17.ure.*;

public class TileEntityRFDriver extends Rotator implements IEnergyHandler{
	int rf=0;

	@Override
	public void updateEntity(){
		if(getWorldObj().isRemote)
			return;
		
		if(rf>=80){
			rf-=80;
			rotate(10);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setInteger("rf", rf);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		rf=nbt.getInteger("rf");
	}

	/** IEnergyHandler */

	@Override
	public boolean canConnectEnergy(ForgeDirection arg0){
		return arg0.equals(ForgeDirection.UP);
	}

	@Override
	public int extractEnergy(ForgeDirection arg0, int i, boolean b){
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection arg0){
		return rf;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection arg0){
		return 400;
	}

	@Override
	public int receiveEnergy(ForgeDirection arg0, int i, boolean b){
		int chg=Math.min(i, getMaxEnergyStored(arg0)-getEnergyStored(arg0));
		if(!b) rf+=chg;
		return chg;
	}
}
