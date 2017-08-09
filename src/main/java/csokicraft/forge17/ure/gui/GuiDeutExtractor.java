package csokicraft.forge17.ure.gui;

import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiDeutExtractor extends UreGuiContainer{
	public static final ResourceLocation bgImage=new ResourceLocation("uraniumre:textures/gui/deut_extr.png");

	public GuiDeutExtractor(ContainerDeutExtractor c){
		super(c);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		drawBackgroundImage(bgImage);
	}

}
