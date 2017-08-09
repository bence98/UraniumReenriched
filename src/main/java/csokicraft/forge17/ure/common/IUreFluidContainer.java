package csokicraft.forge17.ure.common;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
/** An interface to my oversimplified fluid system. Fluid types:
  * 0: water
  * 1: heavywater
  * 2: steam
  * @deprecated Meh, I'll use the MCForge {@link Fluid} as it is the intended way of doing it. */
@Deprecated
public interface IUreFluidContainer{
	public int getMbsStored(int type);
	public boolean canExtract(ForgeDirection side, int type);
	public boolean canInsert(ForgeDirection side, int type);
	public void setMbsStored(int mb, int type);
}
