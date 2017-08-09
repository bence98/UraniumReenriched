package csokicraft.forge17.ure.gui;

import csokicraft.forge17.ure.tileentity.*;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerReactor extends UreContainerBase{
	protected TileEntityReactorBasic te;

	public ContainerReactor(InventoryPlayer ip, TileEntityReactorBasic tile){
		super(ip);
		te=tile;
		if(te instanceof TileEntityReactorSteam){
			addSlotToContainer(new SlotReactorFuel(te, 0, 76, 35));
			addSlotToContainer(new SlotOutput(te, 1, 116, 35));
		}else{
			addSlotToContainer(new SlotReactorInfuse(te, 1, 56, 17));
			addSlotToContainer(new SlotReactorFuel(te, 0, 56, 53));
			addSlotToContainer(new SlotOutput(te, 2, 116, 35));
		}
	}
	
}
