package csokicraft.forge17.ure.gui;

import csokicraft.forge17.ure.tileentity.*;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public class ContainerPlasmafier extends UreContainerBase{
	protected TileEntityPlasmafier te;

	protected int lastProc;
	
	public ContainerPlasmafier(InventoryPlayer ip, TileEntityPlasmafier tile){
		super(ip, tile);
		te=tile;
		addSlotToContainer(new SlotInventory(te, 0, 56, 35));
		addSlotToContainer(new SlotOutput(te, 1, 116, 35));
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
