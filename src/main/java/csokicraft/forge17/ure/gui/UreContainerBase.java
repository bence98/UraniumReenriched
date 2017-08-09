package csokicraft.forge17.ure.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class UreContainerBase extends Container{
	
	public UreContainerBase(InventoryPlayer ip){
		addPlayerSlots(ip);
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
	public boolean canInteractWith(EntityPlayer p){
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p, int i){
		return null;
	}
}
