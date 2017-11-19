package org.usfirst.frc.team5129.subsystem.meta;

/**
 * An autonomous implementation of a subsystem.
 * 
 * @author kyleg
 * 
 */
public abstract class AutoSubsystem extends Subsystem {

	private volatile double power; // Motor power of the autonomous system
	private volatile double curve; // Motor curve

	public AutoSubsystem() {
		super();
	}

	/**
	 * Sets the motor power during routines.
	 * 
	 * @param power
	 *            The power of the motor (Between -1 and 1)
	 */
	public synchronized void setPower(double power) {
		this.power = power;
	}

	/**
	 * Sets the motor's curve.
	 * 
	 * @param curve
	 *            The curve (Between -1 and 1)
	 */
	public synchronized void setCurve(double curve) {
		this.curve = curve;
	}

	/**
	 * 
	 * @return The motor power.
	 */
	public synchronized double getPower() {
		return power;
	}
	
	/**
	 * 
	 * @return The motor curve.
	 */
	public synchronized double getCurve() {
		return curve;
	}

	@Override
	public abstract void complete(int i, final Routine r);

	@Override
	public abstract void onTick();

	@Override
	public abstract boolean onStall();
	
	@Override
	public abstract void onStop();

	@Override
	public abstract String getName();

	@Override
	public abstract String getDescription();
}
