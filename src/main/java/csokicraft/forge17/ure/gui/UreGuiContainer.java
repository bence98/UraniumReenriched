package csokicraft.forge17.ure.gui;

import csokicraft.forge17.ure.common.IHasProgress;
import csokicraft.forge17.ure.tileentity.TileEntityReactorBasic;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public abstract class UreGuiContainer extends GuiContainer{
	protected static final int  ARR_H =15,
								ARR_W =22,
								ARR_X0=80,
								ARR_Y0=35,
								ARR_U0=176,
								ARR_V0=14;

	public UreGuiContainer(Container c){
		super(c);
	}

	protected void drawBackgroundImage(ResourceLocation loc){
		mc.getTextureManager().bindTexture(loc);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	protected void drawArrow(IHasProgress te){
		int wArrow=te.getProgress()*ARR_W/te.getCycleSize();
		drawTexturedModalRect(guiLeft+ARR_X0, guiTop+ARR_Y0, ARR_U0, ARR_V0, wArrow, ARR_H);
	}

}
