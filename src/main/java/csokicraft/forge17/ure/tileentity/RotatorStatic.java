package csokicraft.forge17.ure.tileentity;

/** A rotator with constant output */
public abstract class RotatorStatic extends Rotator{
	/** Set it in the constructor! */
	protected int power;

	protected void rotate(){
		super.rotate(power);
	}
}
