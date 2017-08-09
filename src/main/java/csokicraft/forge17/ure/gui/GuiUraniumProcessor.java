package csokicraft.forge17.ure.gui;

import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiUraniumProcessor extends UreGuiContainer{
	public static final ResourceLocation bgImage=new ResourceLocation("uraniumre:textures/gui/ur_cleaner.png");

	public GuiUraniumProcessor(ContainerUraniumProcessor c){
		super(c);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		drawBackgroundImage(bgImage);
	}

}
