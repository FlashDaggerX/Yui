package org.usfirst.frc.team5129.meta;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;

/**
 * An implementation for an iterative subsystem.
 * 
 * @author kyleg
 */
public abstract class Subsystem extends ControlSafety {

	Routine routine; // The specified routine, if there's one.

	GenericHID control;

	int tick;

	public Subsystem(GenericHID controller) {
		this.control = controller;

		this.routine = new Routine() {

			@Override
			public void doRoutine() {
				DriverStation.reportWarning("subsys_overload_routine", false);
			}
		};
	}

	/**
	 * Runs a number of functions when required.
	 * 
	 * @param i
	 *            The function.
	 */
	public abstract void complete(int i);

	/**
	 * Used to compare
	 * 
	 * @return The subsystem's bit value.
	 */
	public abstract int getID();

	public void tick() {
		onTick();
		tick++;
	}

	public void resetTicks() {
		tick = 0;
	}

	public int getTicks() {
		return tick;
	}

	/**
	 * Called when the subsystem is ticked.
	 */
	public void onTick() {

	}

	@Override
	public void onStop() {

	}

	/**
	 * Sets the routine, if the user wants one.
	 * 
	 * @param r
	 *            The routine to use.
	 */
	public void setRoutine(Routine r) {
		this.routine = r;
	}

	/**
	 * Should be called in autonomous ticks.
	 * 
	 * @return The routine, if one was set.
	 */
	public Routine getRoutine() {
		return routine;
	}

	/**
	 * @return The subsystem's controller
	 */
	public GenericHID getController() {
		return control;
	}

}
