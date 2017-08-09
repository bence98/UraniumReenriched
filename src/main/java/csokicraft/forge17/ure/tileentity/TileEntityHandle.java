package csokicraft.forge17.ure.tileentity;

import csokicraft.forge17.ure.common.IInteractable;
import net.minecraft.entity.player.EntityPlayer;

public class TileEntityHandle extends Rotator implements IInteractable{
	@Override
	public boolean interact(EntityPlayer p){
		if(p.isSneaking())
			return false;
		p.getFoodStats().addExhaustion(1);
		rotate(1);
		return true;
	}
	
}
