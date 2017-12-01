package com.github.flashdaggerx.subsystem.meta;

import com.github.flashdaggerx.safety.ControlSafety;
import com.github.flashdaggerx.subsystem.meta.mock.GenericHID;

/**
 * An implementation for an iterative subsystem.
 * 
 * @author kyleg
 */
public abstract class Subsystem extends ControlSafety {

	private Routine routine; // The specified routine, if there's one.

	private GenericHID control;

	public Subsystem(GenericHID controller) {
		this.control = controller;

		this.routine = new Routine() {

			@Override
			public void doRoutine() {
				System.out.println("Running default routine...");
			}
		};
	}

	/**
	 * Runs a number of functions when required.
	 * 
	 * @param i The function.
	 */
	public abstract void complete(int i);

	/**
	 * Used to compare
	 * 
	 * @return The subsystem's bit value.
	 */
	public abstract byte getID();

	@Override
	public void onStop() {
		System.out.println("overload_subsys_onStop();");
	}

	@Override
	public boolean onStall() {
		return true;
	}

	/**
	 * Sets the routine, if the user wants one.
	 * 
	 * @param r The routine to use.
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
