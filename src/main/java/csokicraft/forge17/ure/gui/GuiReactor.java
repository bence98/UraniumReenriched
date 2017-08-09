package csokicraft.forge17.ure.gui;

import csokicraft.forge17.ure.tileentity.TileEntityReactorBasic;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiReactor extends UreGuiContainer{
	protected static final ResourceLocation[] guiReactorImages=new ResourceLocation[]{
			new ResourceLocation("uraniumre:textures/gui/reactor_mk1.png"),
			new ResourceLocation("uraniumre:textures/gui/reactor_mk2.png"),
			null
	};

	public GuiReactor(ContainerReactor cont){
		super(cont);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseZ){
		int i=getContainerReactor().te.getReactorTier()-1;
		drawBackgroundImage(guiReactorImages[i]);
	}

	protected ContainerReactor getContainerReactor(){
		return (ContainerReactor) inventorySlots;
	}
}
