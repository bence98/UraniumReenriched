package csokicraft.forge17.ure;

import cpw.mods.fml.common.network.IGuiHandler;
import csokicraft.forge17.ure.gui.*;
import csokicraft.forge17.ure.tileentity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler{
	public static final int GUIID_REACTOR=0,
							GUIID_HMR=1,
							GUIID_PLASMA=2,
							GUIID_UR_PROC=3,
							GUIID_DEUT_EX=4;
	
	public void registerModels(){}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		TileEntity te=world.getTileEntity(x, y, z);
		if(te!=null)
			switch(ID){
			case GUIID_REACTOR:
				return new ContainerReactor(player.inventory, (TileEntityReactorBasic) te);
			case GUIID_HMR:
				return new ContainerHMR(player.inventory, (TileEntityHMR) te);
			case GUIID_PLASMA:
				return new ContainerPlasmafier(player.inventory, (TileEntityPlasmafier) te);
			case GUIID_UR_PROC:
				return new ContainerUraniumProcessor(player.inventory, (TileEntityUraniumProcessor) te);
			case GUIID_DEUT_EX:
				return new ContainerDeutExtractor(player.inventory, (TileEntityDeutExtractor) te);
			}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		Object container=getServerGuiElement(ID, player, world, x, y, z);
		if(container!=null)
			switch(ID){
			case GUIID_REACTOR:
				return new GuiReactor((ContainerReactor) container);
			case GUIID_HMR:
				return new GuiHMR((ContainerHMR) container);
			case GUIID_PLASMA:
				return new GuiPlasmafier((ContainerPlasmafier) container);
			case GUIID_UR_PROC:
				return new GuiUraniumProcessor((ContainerUraniumProcessor) container);
			case GUIID_DEUT_EX:
				return new GuiDeutExtractor((ContainerDeutExtractor) container);
			}
		return null;
	}
}
