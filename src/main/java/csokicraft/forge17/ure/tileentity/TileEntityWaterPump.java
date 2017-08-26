package csokicraft.forge17.ure.tileentity;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import csokicraft.forge17.ure.*;
import csokicraft.forge17.ure.common.IHasProgress;
import csokicraft.forge17.ure.common.IRotatable;

public class TileEntityWaterPump extends TileEntity implements IRotatable, IFluidHandler, IHasProgress{
	public static final int CAPACITY=16000;
	
	protected int mbWater, proc;

	public void onRotated(int pow){
		if(mbWater<=15000 && (worldObj.getBlock(xCoord, yCoord-1, zCoord).equals(Blocks.water)||(worldObj.getBlock(xCoord, yCoord-1, zCoord).equals(Blocks.flowing_water)&&worldObj.getBlockMetadata(xCoord, yCoord-1, zCoord)==0))) proc+=pow;
	}
	
	@Override
	public void updateEntity(){
		if(getWorldObj().isRemote)
			return;
		
		if(proc>=getCycleSize()){
			worldObj.setBlockToAir(xCoord, yCoord-1, zCoord);
			mbWater+=1000;
			proc-=100;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setInteger("proc", proc);
		nbt.setInteger("water", mbWater);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		proc=nbt.getInteger("proc");
		mbWater=nbt.getInteger("water");
	}
	
	/** IHasProgress */
	
	@Override
	public int getProgress(){
		return proc;
	}
	
	@Override
	public int getCycleSize(){
		return 100;
	}
	
	/** IFluidHandler */

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill){
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain){
		if(resource!=null&&mbWater>0&&resource.getFluid().equals(FluidRegistry.WATER)){
			int trans=Math.max(mbWater, resource.amount);
			if(doDrain) mbWater-=trans;
			return new FluidStack(FluidRegistry.WATER, trans);
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain){
		if(mbWater>0){
			int trans=Math.min(mbWater, maxDrain);
			if(doDrain) mbWater-=trans;
			return new FluidStack(FluidRegistry.WATER, trans);
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid){
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid){
		return fluid.equals(FluidRegistry.WATER);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from){
		return new FluidTankInfo[]{	new FluidTankInfo(new FluidStack(FluidRegistry.WATER, mbWater), CAPACITY)};
	}
}