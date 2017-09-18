package csokicraft.forge17.ure.gui;

import csokicraft.forge17.ure.tileentity.*;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public class ContainerDeutExtractor extends UreContainerBase{
	protected TileEntityDeutExtractor te;
	
	protected int lastProc, lastHeavy;

	public ContainerDeutExtractor(InventoryPlayer ip, TileEntityDeutExtractor tile){
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
		case 1:
			te.mbHeavy=val;
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
			if(te.mbHeavy!=lastHeavy){
				listener.sendProgressBarUpdate(this, 1, te.mbHeavy);
				lastHeavy=te.mbHeavy;
			}
		}
	}
}
