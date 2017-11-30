package org.usfirst.frc.team5129.subsystem.meta;

import org.usfirst.frc.team5129.safety.ControlSafety;
import org.usfirst.frc.team5129.safety.MotorState;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;

/**
 * An implementation for an iterative subsystem.
 * 
 * @author kyleg
 */
public abstract class Subsystem extends ControlSafety {

	private MotorState state = MotorState.STOPPED;

	private Routine routine; // The specified routine, if there's one.

	private GenericHID control;

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
	 * @param i The function.
	 */
	public abstract void complete(byte i);

	/**
	 * Used to compare
	 * 
	 * @return The subsystem's bit value.
	 */
	public abstract byte getID();

	@Override
	public void onStop() {
		DriverStation.reportWarning("overload_subsys_onStop()", false);
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
	 * @return State of the subsystem
	 */
	public MotorState getMotorState() {
		return state;
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
