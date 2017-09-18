package csokicraft.forge17.ure.gui;

import csokicraft.forge17.ure.tileentity.*;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerUraniumProcessor extends UreContainerBase{
	protected TileEntityUraniumProcessor te;

	public ContainerUraniumProcessor(InventoryPlayer ip, TileEntityUraniumProcessor tile){
		super(ip, tile);
		te=tile;
		addSlotToContainer(new SlotInventory(te, 0, 56, 35));
		addSlotToContainer(new SlotOutput(te, 1, 116, 35));
		
	}
	
}
