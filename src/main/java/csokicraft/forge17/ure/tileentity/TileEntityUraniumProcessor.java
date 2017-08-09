package csokicraft.forge17.ure.tileentity;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import net.minecraftforge.oredict.OreDictionary;
import csokicraft.forge17.ure.*;
import csokicraft.forge17.ure.common.*;

public class TileEntityUraniumProcessor extends TileEntityInv implements IRotatable, IFluidHandler, IHasProgress, ISidedInventory, IHasGui{
	protected static final int CAPACITY=8000;
	
	private int proc, mbWater, mbHeavy;

	public TileEntityUraniumProcessor(){
		slots=new ItemStack[2];
	}

	public void onRotated(int pow){
		if(inputValid()&&outputValid()) proc+=pow;
	}

	@Override
	public void updateEntity(){
		if(!(inputValid()&&outputValid())) proc=0;
		else if(proc>=getCycleSize()){
			slots[0].stackSize--;
			if(slots[0].stackSize<=0) slots[0]=null;
			if(slots[1]==null) slots[1]=new ItemStack(UraniumRE.itemComponent, 8, 5);
			else slots[1].stackSize+=8;
			mbWater-=1000;
			mbHeavy+=1000;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setInteger("proc", proc);
		nbt.setInteger("water", mbWater);
		nbt.setInteger("heavy", mbHeavy);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		proc=nbt.getInteger("proc");
		mbWater=nbt.getInteger("water");
		mbHeavy=nbt.getInteger("heavy");
	}

	private boolean inputValid() {
		return slots[0]!=null&&isUranium(slots[0]);
	}
	
	public static boolean isUranium(ItemStack is){
		int[] ids = OreDictionary.getOreIDs(is);
		int arr[]=new int[]{
				OreDictionary.getOreID("oreUranium"),
				OreDictionary.getOreID("oreUraninite"),
				OreDictionary.getOreID("oreYellorite"),
				OreDictionary.getOreID("orePitchblende")
		};
		for(int i:ids)
			for(int x:arr)
				if(i==x) return true;
		return false;
	}

	private boolean outputValid(){
		return (slots[1]==null||slots[1].stackSize<57)&&mbWater>=1000&&mbHeavy<=(CAPACITY-1000);
	}
	
	/** IInventory */
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack is){
		return isUranium(is);
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
		if(resource!=null&&resource.getFluid().equals(FluidRegistry.WATER)){
			int trans=Math.min(CAPACITY-mbWater, resource.amount);
			if(doFill) mbWater+=trans;
			return trans;
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain){
		if(resource!=null&&mbHeavy>0&&resource.getFluid().equals(UraniumRE.heavyWater)){
			int trans=Math.min(mbHeavy, resource.amount);
			if(doDrain) mbHeavy-=trans;
			return new FluidStack(UraniumRE.heavyWater, trans);
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain){
		if(mbHeavy>0){
			int trans=Math.min(mbHeavy, maxDrain);
			if(doDrain) mbHeavy-=trans;
			return new FluidStack(UraniumRE.heavyWater, trans);
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid){
		return fluid.equals(FluidRegistry.WATER);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid){
		return fluid.equals(UraniumRE.heavyWater);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from){
		return new FluidTankInfo[]{
			new FluidTankInfo(new FluidStack(FluidRegistry.WATER, mbWater), CAPACITY),
			new FluidTankInfo(new FluidStack(UraniumRE.heavyWater, mbHeavy), CAPACITY)
		};
	}

	@Override
	public String getInventoryName(){
		return "container.ure.uranium_cleaner.name";
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
		return s!=ForgeDirection.DOWN.ordinal()&&i==0&&isItemValidForSlot(i, is);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack is, int s){
		return i==1&&s==ForgeDirection.DOWN.ordinal();
	}
	
	/** IHasGui */
	
	@Override
	public int GetGuiID(){
		return CommonProxy.GUIID_UR_PROC;
	}
}
