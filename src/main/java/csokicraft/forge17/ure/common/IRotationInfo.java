package csokicraft.forge17.ure.common;

import csokicraft.forge17.ure.tileentity.Rotator;

public interface IRotationInfo{
	/** True if {@link Rotator}, false if {@link IRotatable}*/
	public boolean provides();
	public int amount();
}
