package csokicraft.forge17.ure.common;


/** Implementation of {@link IRotationInfo} */
public class RotationInfo implements IRotationInfo{
	private int qty;
	private boolean prov;

	public RotationInfo(boolean provides, int amount){
		prov=provides;
		qty=amount;
	}
	
	@Override
	public boolean provides(){
		return prov;
	}

	@Override
	public int amount(){
		return qty;
	}

}
