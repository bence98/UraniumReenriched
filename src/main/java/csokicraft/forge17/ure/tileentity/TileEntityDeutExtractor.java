package csokicraft.forge17.ure.tileentity;

import csokicraft.forge17.ure.CommonProxy;
import csokicraft.forge17.ure.UraniumRE;
import csokicraft.forge17.ure.common.IHasGui;
import csokicraft.forge17.ure.common.IHasProgress;
import csokicraft.forge17.ure.common.IRotatable;
import csokicraft.forge17.ure.item.UREItems;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityDeutExtractor extends TileEntityInv implements IRotatable, IFluidHandler, IHasProgress, ISidedInventory, IHasGui{
	public static final int CAPACITY=4000;
	
	protected int proc=0, mbHeavy=0;

	public TileEntityDeutExtractor(){
		slots=new ItemStack[2];
	}
	
	@Override
	public void onRotated(int work){
		if(inputValid()&&outputValid()) proc+=work/5;
	}
	
	@Override
	public void updateEntity(){
		if(inputValid()&&outputValid()){
			if(proc>=getCycleSize()){
				mbHeavy-=1000;
				if(slots[1]==null)
					slots[1]=UREItems.cell_deu.copy();
				else slots[1].stackSize++;
				slots[0].stackSize--;
				if(slots[0].stackSize<=0) slots[0]=null;
				proc-=getCycleSize();
			}
		}else proc=0;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setInteger("proc", proc);
		nbt.setInteger("heavy", mbHeavy);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		proc=nbt.getInteger("proc");
		mbHeavy=nbt.getInteger("heavy");
	}

	protected boolean inputValid(){
		return slots[0]!=null&&slots[0].isItemEqual(UREItems.cell_emp)&&mbHeavy>=1000;
	}
	
	protected boolean outputValid(){
		return slots[1]==null||(slots[1].isItemEqual(UREItems.cell_deu)&&slots[1].stackSize<slots[1].getMaxStackSize());
	}
	
	@Override
	public String getInventoryName(){
		return "container.ure.deut_extractor.name";
	}
	
	/** IHasProgress */
	
	@Override
	public int getProgress(){
		return proc;
	}
	
	@Override
	public int getCycleSize(){
		return 200;
	}
	
	/** IFluidHandler */

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill){
		if(resource!=null&&resource.getFluid().equals(UraniumRE.heavyWater)){
			int trans=Math.min(CAPACITY-mbHeavy, resource.amount);
			if(doFill) mbHeavy+=trans;
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
		return fluid.equals(UraniumRE.heavyWater);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid){
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from){
		return new FluidTankInfo[]{new FluidTankInfo(new FluidStack(UraniumRE.heavyWater, mbHeavy), CAPACITY)};
	}
	/** ISidedInventory */
	
	@Override
	public int[] getAccessibleSlotsFromSide(int s){
		if(s==ForgeDirection.DOWN.ordinal())
			return new int[]{1};
		return new int[]{0};
	}

	@Override
	public boolean canInsertItem(int i, ItemStack is, int s){
		return s!=ForgeDirection.DOWN.ordinal()&&i==0&&is.isItemEqual(UREItems.cell_emp);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack is, int s){
		return i==1&&s==ForgeDirection.DOWN.ordinal();
	}
	
	/** IHasGui */
	
	@Override
	public int GetGuiID(){
		return CommonProxy.GUIID_DEUT_EX;
	}
}
