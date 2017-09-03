package csokicraft.forge17.ure.gui;

import csokicraft.forge17.ure.tileentity.TileEntityReactorBasic;
import csokicraft.forge17.ure.tileentity.TileEntityReactorSteam;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiReactor extends UreGuiContainer{
	/** BAR_H: full height of the bars
	  * BAR_W: full width of the bars
	  * X0: X coord of the first bar (radiation)
	  * Y0: Y coord of the first bar (radiation)
	  * U0: U tex-coord of the first bar (radiation)
	  * V0: V tex-coord of the first bar (radiation)
	  * DX: the difference of X coords (& U tex-coords) between the two bars */
	public static final int BAR_H =49,
							BAR_W =8,
							BAR_X0=150,
							BAR_Y0=15,
							BAR_U0=176,
							BAR_V0=31,
							BAR_DX=12,
							
							FIRE_H=14,
							FIRE_W=14,
							FIRE_X0=95,
							FIRE_Y0=35,
							FIRE_U0=176,
							FIRE_V0=0,
							
							TANK_H= 64,
							TANK_WW=18,
							TANK_WS=34,
							TANK_X0=9,
							TANK_Y0=10,
							TANK_U0=176,
							TANK_V0=81,
							TANK_DX=21;
	
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
		drawRadCtmBars();
		switch(getContainerReactor().te.getReactorTier()){
		case 1:
			drawArrow();
			break;
		case 2:
			drawFire();
			drawTanks();
			break;
		}
	}

	protected ContainerReactor getContainerReactor(){
		return (ContainerReactor) inventorySlots;
	}
	
	private void drawRadCtmBars(){
		TileEntityReactorBasic te=getContainerReactor().te;
		int hRad=te.rad*BAR_H/te.MAX_RAD;
		int hCtm=te.ctm*BAR_H/te.MAX_CTM;
		int dyRad=BAR_H-hRad, dyCtm=BAR_H-hCtm;
		drawTexturedModalRect(guiLeft+BAR_X0,        guiTop+BAR_Y0+dyRad, BAR_U0,        BAR_V0+dyRad, BAR_W, hRad);
		drawTexturedModalRect(guiLeft+BAR_X0+BAR_DX, guiTop+BAR_Y0+dyCtm, BAR_U0+BAR_DX, BAR_V0+dyCtm, BAR_W, hCtm);
	}
	
	private void drawArrow(){
		TileEntityReactorBasic te=getContainerReactor().te;
		drawArrow(te);
	}
	
	private void drawFire(){
		TileEntityReactorBasic te=getContainerReactor().te;
		if(te.getProgress()==0) return;
		int hFire=FIRE_H-te.getProgress()*FIRE_H/te.getCycleSize();
		int dyFire=FIRE_H-hFire;
		drawTexturedModalRect(guiLeft+FIRE_X0, guiTop+FIRE_Y0+dyFire, FIRE_U0, FIRE_V0+dyFire, FIRE_W, hFire);
	}
	
	private void drawTanks(){
		TileEntityReactorSteam te=(TileEntityReactorSteam) getContainerReactor().te;
		int hWater=te.mbWater*TANK_H/te.CAPACITY;
		int hSteam=te.mbSteam*TANK_H/te.CAPACITY;
		int dyWater=TANK_H-hWater, dySteam=TANK_H-hSteam;
		drawTexturedModalRect(guiLeft+TANK_X0,         guiTop+TANK_Y0+dyWater, TANK_U0,         TANK_V0+dyWater, TANK_WW, hWater);
		drawTexturedModalRect(guiLeft+TANK_X0+TANK_DX, guiTop+TANK_Y0+dySteam, TANK_U0+TANK_DX, TANK_V0+dySteam, TANK_WS, hSteam);
	}
}
