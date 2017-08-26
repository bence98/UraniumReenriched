package csokicraft.forge17.ure.tileentity;

import csokicraft.forge17.ure.*;

public class TileEntityAdvTurbine extends TileEntitySteamTurbine{

	@Override
	public void updateEntity(){
		if(getWorldObj().isRemote)
			return;
		
		if(steamMb>=80){
			steamMb-=80;
			rotate(20);
		}
	}
}
