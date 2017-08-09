package csokicraft.forge17.ure.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotOutput extends Slot{

	public SlotOutput(IInventory arg0, int arg1, int arg2, int arg3){
		super(arg0, arg1, arg2, arg3);
	}
	
	@Override
	public boolean isItemValid(ItemStack p_75214_1_){
		return false;
	}
}
