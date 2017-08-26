package csokicraft.forge17.ure.tileentity;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import csokicraft.forge17.ure.*;
import csokicraft.forge17.ure.common.IHasGui;
import csokicraft.forge17.ure.common.IHasProgress;
import csokicraft.forge17.ure.common.IRotatable;
import csokicraft.forge17.ure.item.UREItems;

public class TileEntityPlasmafier extends TileEntityInv implements IRotatable, IHasProgress, ISidedInventory, IHasGui{
	private int proc;

	public TileEntityPlasmafier(){
		slots=new ItemStack[2];
	}

	public void onRotated(int pow){
		if(pow>=10&&inputValid()&&(slots[1]==null||slots[1].stackSize<64)) proc+=pow/10;
	}

	@Override
	public void updateEntity(){
		if(getWorldObj().isRemote)
			return;
		
		if(proc>=getCycleSize()){
			slots[0].stackSize--;
			if(slots[0].stackSize<=0) slots[0]=null;
			
			if(slots[1]==null) slots[1]=UREItems.cell_pla.copy();
			else slots[1].stackSize++;
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

	private boolean inputValid(){
		return slots[0]!=null&&slots[0].isItemEqual(UREItems.cell_deu);
	}
	
	@Override
	public String getInventoryName(){
		return "container.ure.plasma_heater.name";
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
		if(s==ForgeDirection.DOWN.ordinal())
			return new int[]{1};
		return new int[]{0};
	}

	@Override
	public boolean canInsertItem(int i, ItemStack is, int s){
		return s!=ForgeDirection.DOWN.ordinal()&&i==0&&is.isItemEqual(UREItems.cell_deu);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack is, int s){
		return i==1&&s==ForgeDirection.DOWN.ordinal();
	}
	
	/** IHasGui */
	
	@Override
	public int GetGuiID(){
		return CommonProxy.GUIID_PLASMA;
	}
}
