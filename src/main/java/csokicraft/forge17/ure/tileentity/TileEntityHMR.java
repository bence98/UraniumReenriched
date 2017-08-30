package csokicraft.forge17.ure.tileentity;

import java.util.List;
import java.util.Random;

import csokicraft.forge17.ure.CommonProxy;
import csokicraft.forge17.ure.common.IHasGui;
import csokicraft.forge17.ure.common.IHasProgress;
import csokicraft.forge17.ure.common.IRotatable;
import csokicraft.forge17.ure.recipe.RecyclingRecipes;
import csokicraft.forge17.ure.recipe.RecyclingRecipes.*;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHMR extends TileEntityInv implements IRotatable, IHasProgress, ISidedInventory, IHasGui{
	private int proc;

	public TileEntityHMR(){
		slots=new ItemStack[5];
	}

	@Override
	public void onRotated(int work){
		RecyclingRecipe rec=RecyclingRecipes.inst.getRecipe(slots[0]);
		if(rec!=null) proc+=work/2;
	}
	
	@Override
	public void updateEntity(){
		if(getWorldObj().isRemote)
			return;
		
		RecyclingRecipe rec=RecyclingRecipes.inst.getRecipe(slots[0]);
		if(rec!=null&&proc>=getCycleSize()){
			slots[0].stackSize--;
			proc-=200;
			List<WeightedItemStackResult> l=rec.getOutputs();
			for(WeightedItemStackResult res:l){
				double baseQty=Math.floor(res.getAmountChance());
				double chanceQty=res.getAmountChance()-baseQty;
				if(new Random().nextDouble()>=chanceQty) baseQty++;
				ItemStack toAdd=res.getTypeStack().copy();
				toAdd.stackSize=(int) baseQty;
				addStack(toAdd);
			}
			if(slots[0].stackSize<=0) slots[0]=null;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setInteger("proc", proc);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		proc=nbt.getInteger("proc");
	}

	/** IINventory */
	
	@Override
	public String getInventoryName(){
		return "container.ure.hmr.name";
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack is){
		return i==0&&RecyclingRecipes.inst.hasRecipe(is);
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

	/** ISidedInventory */
	
	@Override
	public int[] getAccessibleSlotsFromSide(int s){
		if(s!=ForgeDirection.DOWN.ordinal())
			return new int[]{0};
		return new int[]{1, 2, 3, 4};
	}

	@Override
	public boolean canInsertItem(int i, ItemStack is, int s){
		if(s!=ForgeDirection.DOWN.ordinal())
			return i==0;
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack is, int s){
		if(s==ForgeDirection.DOWN.ordinal())
			return i!=0;
		return false;
	}
	
	/** IHasGui */
	
	@Override
	public int GetGuiID(){
		return CommonProxy.GUIID_HMR;
	}
}
