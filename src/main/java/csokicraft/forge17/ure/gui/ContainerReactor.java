package csokicraft.forge17.ure.gui;

import csokicraft.forge17.ure.tileentity.*;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public class ContainerReactor extends UreContainerBase{
	protected TileEntityReactorBasic te;
	
	protected int lastProc, lastRad, lastCtm, lastWater, lastSteam;

	public ContainerReactor(InventoryPlayer ip, TileEntityReactorBasic tile){
		super(ip);
		te=tile;
		if(te instanceof TileEntityReactorSteam){
			addSlotToContainer(new SlotInventory(te, 0, 76, 35));
			addSlotToContainer(new SlotOutput(te, 1, 116, 35));
		}else{
			addSlotToContainer(new SlotInventory(te, 1, 56, 17));
			addSlotToContainer(new SlotInventory(te, 0, 56, 53));
			addSlotToContainer(new SlotOutput(te, 2, 116, 35));
		}
	}
	
	@Override
	public void updateProgressBar(int i, int val){
		switch (i) {
		case 0:
			te.proc=val;
			break;
		case 1:
			te.rad=val;
			break;
		case 2:
			te.ctm=val;
			break;
		case 3:
			((TileEntityReactorSteam)te).mbWater=val;
			break;
		case 4:
			((TileEntityReactorSteam)te).mbSteam=val;
			break;
		}
	}
	
	@Override
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		for(Object tmp:crafters){
			ICrafting iCrafting=(ICrafting) tmp;
			if(te.proc!=lastProc)
				iCrafting.sendProgressBarUpdate(this, 0, te.proc);
			if(te.rad!=lastRad)
				iCrafting.sendProgressBarUpdate(this, 1, te.rad);
			if(te.ctm!=lastCtm)
				iCrafting.sendProgressBarUpdate(this, 2, te.ctm);
			if(te instanceof TileEntityReactorSteam){
				TileEntityReactorSteam te2=(TileEntityReactorSteam) te;
				if(te2.mbWater!=lastWater)
					iCrafting.sendProgressBarUpdate(this, 3, te2.mbWater);
				if(te2.mbSteam!=lastSteam)
					iCrafting.sendProgressBarUpdate(this, 4, te2.mbSteam);
			}
		}
		
		lastProc=te.proc;
		lastRad=te.rad;
		lastCtm=te.ctm;
		if(te instanceof TileEntityReactorSteam){
			TileEntityReactorSteam te2=(TileEntityReactorSteam) te;
			lastWater=te2.mbWater;
			lastSteam=te2.mbSteam;
		}
	}
}
