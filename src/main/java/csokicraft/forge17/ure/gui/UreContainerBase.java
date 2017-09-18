package csokicraft.forge17.ure.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public abstract class UreContainerBase extends Container{
	protected int invSize;
	
	public UreContainerBase(InventoryPlayer ip, IInventory inv){
		addPlayerSlots(ip);
		invSize=inv.getSizeInventory();
	}

	@Override
	public boolean canInteractWith(EntityPlayer p){
		return true;
	}

	/** From ContainerFurnace */
	
	protected void addPlayerSlots(InventoryPlayer ip){
		int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(ip, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(ip, i, 8 + i * 18, 142));
        }
	}
	
	@Override
	protected boolean mergeItemStack(ItemStack stack, int start, int end, boolean reverse)
    {
        boolean flag1 = false;
        int k = start;

        if (reverse)
        {
            k = end - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (stack.isStackable())
        {
            while (stack.stackSize > 0 && (!reverse && k < end || reverse && k >= start))
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 != null && itemstack1.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, itemstack1))
                {
                    int l = itemstack1.stackSize + stack.stackSize;

                    if (l <= stack.getMaxStackSize())
                    {
                        stack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                    else if (itemstack1.stackSize < stack.getMaxStackSize())
                    {
                        stack.stackSize -= stack.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = stack.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }

                if (reverse)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        if (stack.stackSize > 0)
        {
            if (reverse)
            {
                k = end - 1;
            }
            else
            {
                k = start;
            }

            while (!reverse && k < end || reverse && k >= start)
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                // Fixed: not checking if Slot would actually take it
                if (itemstack1 == null&&slot.isItemValid(stack))
                {
                    slot.putStack(stack.copy());
                    slot.onSlotChanged();
                    stack.stackSize = 0;
                    flag1 = true;
                    break;
                }

                if (reverse)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }
        return flag1;
    }
	
	public ItemStack transferStackInSlot(EntityPlayer p, int idx)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(idx);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (idx >= 36 && idx < 36+invSize) //te -> player
            {
                if (!this.mergeItemStack(itemstack1, 0, 36, false))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if(idx >= 0 && idx < 36) //player -> te
            {
                if (!this.mergeItemStack(itemstack1, 36, 36+invSize, false)) //try adding it
                {
                    if (idx >= 0 && idx < 27 && !this.mergeItemStack(itemstack1, 27, 36, false))
	                { //or if in main inv, just move it to hotbar
	                    return null;
	                } //if in hotbar, move it to main inv
	                else if (idx >= 27 && idx < 36 && !this.mergeItemStack(itemstack1, 0, 27, false))
	                {
	                    return null;
	                }
                }
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(p, itemstack1);
        }

        return itemstack;
    }
}
