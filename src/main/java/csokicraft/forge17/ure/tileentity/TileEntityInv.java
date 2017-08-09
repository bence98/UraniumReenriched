package csokicraft.forge17.ure.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;

public abstract class TileEntityInv extends TileEntity implements IInventory{
	protected ItemStack slots[];

	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		NBTTagList lst=new NBTTagList();
		for(int i=0;i<getSizeInventory();i++){
			ItemStack is=getStackInSlot(i);
			if(is==null) continue;
			NBTTagCompound pair=new NBTTagCompound();
			pair.setInteger("slot", i);
			pair.setTag("item", is.writeToNBT(new NBTTagCompound()));
			lst.appendTag(pair);
		}
		nbt.setTag("inv", lst);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		NBTTagList lst=nbt.getTagList("inv", NBT.TAG_COMPOUND);
		for(int i=0;i<lst.tagCount();i++){
			NBTTagCompound pair=lst.getCompoundTagAt(i);
			setInventorySlotContents(pair.getInteger("slot"), ItemStack.loadItemStackFromNBT(pair.getCompoundTag("item")));
		}
	}
	
	@Override
	public int getSizeInventory(){
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i){
		return slots[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int n){
		ItemStack is=getStackInSlot(i),
				  ret=is.copy();
		
		if(n>is.stackSize){
			setInventorySlotContents(i, null);
		}else{
			is.stackSize-=n;
			ret.stackSize=n;
			setInventorySlotContents(i, is);
		}
		
		return ret;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i){
		return getStackInSlot(i);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack is){
		slots[i]=is;
	}

	@Override
	public boolean hasCustomInventoryName(){
		return false;
	}

	@Override
	public int getInventoryStackLimit(){
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p){
		return true;
	}

	@Override
	public void openInventory(){}

	@Override
	public void closeInventory(){}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is){
		return true;
	}

	public boolean addStack(ItemStack toAdd){
		for(int i=0;i<getSizeInventory();i++){
			ItemStack slotStack=getStackInSlot(i);
			if(slotStack==null){
				setInventorySlotContents(i, toAdd);
				return true;
			}
			else if(slotStack.isItemEqual(toAdd)){
				slotStack.stackSize+=toAdd.stackSize;
				setInventorySlotContents(i, slotStack);
				return true;
			}
		}
		return false;
	}
}
