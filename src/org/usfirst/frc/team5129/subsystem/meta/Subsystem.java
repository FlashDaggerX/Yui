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

	private Routine routine; // The specified routine, if there's one.

	private GenericHID control;

	private int tick;

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

	public void tick() {
		tick++;
		if (getMotorState() == MotorState.RUNNING)
			DriverStation.reportWarning(
					"STATE=RUNNING:ticking_while_stopped",
					false);
	}

	public void resetTicks() {
		tick = 0;
	}

	public int getTicks() {
		return tick;
	}

	@Override
	public void onStop() {
		DriverStation.reportWarning("overload_subsys_onStop()", false);
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
