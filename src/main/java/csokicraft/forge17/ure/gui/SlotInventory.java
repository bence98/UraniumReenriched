package csokicraft.forge17.ure.gui;

import csokicraft.forge17.ure.tileentity.TileEntityUraniumProcessor;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotInventory extends Slot{

	public SlotInventory(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack is){
		return inventory.isItemValidForSlot(slotNumber, is);
	}
}
