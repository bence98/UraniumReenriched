package csokicraft.forge17.ure.gui;

import csokicraft.forge17.ure.tileentity.*;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerHMR extends UreContainerBase{
	protected TileEntityHMR te;

	public ContainerHMR(InventoryPlayer ip, TileEntityHMR tile){
		super(ip);
		te=tile;
		addSlotToContainer(new SlotRecycle(te, 0, 56, 35));
		addSlotToContainer(new SlotOutput(te, 1, 116, 35));
		
	}
	
}
