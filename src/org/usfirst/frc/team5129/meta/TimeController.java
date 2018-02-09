package org.usfirst.frc.team5129.meta;

/**
 * Holds tick and distance values.
 * @author kyleg
 *
 */
public abstract class TimeController extends ControlSafety {
	
	int tick, distance;
	
	public TimeController() {
		this.tick = 0;
		this.distance = 0;
	}
	
	/**
	 * Called when the subsystem is ticked.
	 */
	public void onTick() {

	}
	
	/**
	 * Ticks the subsystem
	 * 
	 * @see TimeController#onTick()
	 */
	public void tick() {
		onTick();
		tick++;
	}
	
	/**
	 * Reset tick counter.
	 */
	public void resetTicks() {
		tick = 0;
	}

	/**
	 * 
	 * @return The amount of ticks that have passed.
	 */
	public int getTicks() {
		return tick;
	}
	
	/**
	 * Set the distance value
	 * @param distance
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	/**
	 * Increment distance
	 */
	public void incDistance() {
		distance++;
		System.out.println("inc");
	}
	
	/**
	 * Decrement distance
	 */
	public void decDistance() {
		distance--;
		System.out.println("dec");
	}
	
	/**
	 * 
	 * @return The distance.
	 */
	public int getDistance() {
		return distance;
	}

}
