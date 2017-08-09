package csokicraft.forge17.ure.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public abstract class UreGuiContainer extends GuiContainer{

	public UreGuiContainer(Container c){
		super(c);
	}

	protected void drawBackgroundImage(ResourceLocation loc){
		mc.getTextureManager().bindTexture(loc);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

}
