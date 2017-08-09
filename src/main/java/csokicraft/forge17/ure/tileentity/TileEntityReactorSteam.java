package csokicraft.forge17.ure.tileentity;

import csokicraft.forge17.ure.UraniumRE;
import csokicraft.forge17.ure.recipe.IReactorFuelRecipe;
import csokicraft.forge17.ure.recipe.ReactorFuelRecipe;
import csokicraft.forge17.ure.recipe.ReactorFuelRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntityReactorSteam extends TileEntityReactorBasic implements IFluidHandler{
	protected static final int CAPACITY=8000;
	protected int mbWater, mbSteam;
	
	public TileEntityReactorSteam(){
		slots=new ItemStack[2];
	}

	@Override
	public String getInventoryName(){
		return "tile.uretech.reactor_two.name";
	}
	
	@Override
	protected void checkInfuse(){
		if(proc>=getCycleSize()){
			proc-=getCycleSize();
			mbWater-=100;
			mbSteam+=100;
		}else{
			if(rad>0&&mbWater>=100&&mbSteam+100<CAPACITY){
				rad--;
				proc++;
			}
		}
	}
	
	protected void checkFuel(){
		IReactorFuelRecipe fuel=ReactorFuelRecipes.inst.getRecipe(slots[0]);
		if(slots[1]==null&&fuel!=null&&fuel.getTier()<=getReactorTier()){
			rad+=fuel.getPower();
			ctm+=fuel.getWaste();
			slots[0].stackSize--;
			if(fuel.getOutput()!=null)
				slots[1]=fuel.getOutput().copy();
		}
		if(slots[0]!=null&&slots[0].stackSize<=0) slots[0]=null;
	}
	
	@Override
	public int getCycleSize(){
		return 20;
	}

	@Override
	public int getReactorTier(){
		return 2;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		nbt.setInteger("steam", mbSteam);
		nbt.setInteger("water", mbWater);
		super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		mbSteam=nbt.getInteger("steam");
		mbWater=nbt.getInteger("water");
		super.readFromNBT(nbt);
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
		if(resource!=null&&mbSteam>0&&resource.getFluid().equals(UraniumRE.steam)){
			int trans=Math.min(mbSteam, resource.amount);
			if(doDrain) mbSteam-=trans;
			return new FluidStack(UraniumRE.steam, trans);
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain){
		if(mbSteam>0){
			int trans=Math.min(mbSteam, maxDrain);
			if(doDrain) mbSteam-=trans;
			return new FluidStack(UraniumRE.steam, trans);
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid){
		return fluid.equals(FluidRegistry.WATER);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid){
		return fluid.equals(UraniumRE.steam);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from){
		return new FluidTankInfo[]{
			new FluidTankInfo(new FluidStack(FluidRegistry.WATER, mbWater), CAPACITY),
			new FluidTankInfo(new FluidStack(UraniumRE.steam, mbSteam), CAPACITY)
		};
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
		if(s==ForgeDirection.DOWN.ordinal()) return false;
		return i==0;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack is, int s){
		return s==ForgeDirection.DOWN.ordinal()&&i==1;
	}
}
