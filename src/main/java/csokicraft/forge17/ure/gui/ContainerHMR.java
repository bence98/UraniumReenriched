package csokicraft.forge17.ure.gui;

import csokicraft.forge17.ure.tileentity.*;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public class ContainerHMR extends UreContainerBase{
	protected TileEntityHMR te;
	
	protected int lastProc;

	public ContainerHMR(InventoryPlayer ip, TileEntityHMR tile){
		super(ip);
		te=tile;
		addSlotToContainer(new SlotInventory(te, 0, 56, 35));
		addSlotToContainer(new SlotOutput(te, 1, 116, 16));
		addSlotToContainer(new SlotOutput(te, 2, 116, 54));
		addSlotToContainer(new SlotOutput(te, 3, 147, 16));
		addSlotToContainer(new SlotOutput(te, 4, 147, 54));
	}
	
	@Override
	public void updateProgressBar(int i, int val){
		switch (i) {
		case 0:
			te.proc=val;
			break;
		}
	}
	
	@Override
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		for(Object obj:crafters){
			ICrafting listener=(ICrafting) obj;
			if(te.proc!=lastProc){
				listener.sendProgressBarUpdate(this, 0, te.proc);
				lastProc=te.proc;
			}
		}
	}
}
