package csokicraft.forge17.ure.tileentity;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import csokicraft.forge17.ure.*;
import csokicraft.forge17.ure.common.IRotatable;

public class TileEntityRFModulator extends TileEntity implements IEnergyHandler, IRotatable{
	int rf=0;

	public void onRotated(int pow){
		if(rf<2000) rf+=(pow*8);
		else rf=2000;
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
		return arg0.equals(ForgeDirection.DOWN);
	}

	@Override
	public int extractEnergy(ForgeDirection arg0, int i, boolean b){
		int chg=Math.min(i, getEnergyStored(arg0));
		if(!b) rf-=chg;
		return chg;
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
		return 0;
	}
}
