package csokicraft.forge17.ure.tileentity;

import csokicraft.forge17.ure.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntitySteamTurbine extends Rotator implements IFluidHandler{
	public static final int CAPACITY=2000;
	
	int steamMb=0;

	@Override
	public void updateEntity(){
		if(steamMb>=20){
			steamMb-=20;
			rotate(5);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		nbt.setInteger("steam", steamMb);
		super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		if(nbt.hasKey("steam"))
			steamMb=nbt.getInteger("steam");
		super.readFromNBT(nbt);
	}

	/** IFluidHandler */
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill){
		if(resource!=null&&resource.getFluid().equals(UraniumRE.steam)){
			int trans=Math.min(CAPACITY-steamMb, resource.amount);
			if(doFill) steamMb+=trans;
			return trans;
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain){
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain){
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid){
		return fluid.equals(UraniumRE.steam);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid){
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from){
		return new FluidTankInfo[]{new FluidTankInfo(new FluidStack(UraniumRE.steam, steamMb), CAPACITY)};
	}
}
