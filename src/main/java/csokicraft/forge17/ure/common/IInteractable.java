package csokicraft.forge17.ure.common;

import net.minecraft.entity.player.EntityPlayer;

public interface IInteractable{
	/** @return true to skip further handlers */
	public boolean interact(EntityPlayer p);
}
