package org.usfirst.frc.team5129.subsystem.meta;

import org.usfirst.frc.team5129.safety.ControlSafety;
import org.usfirst.frc.team5129.safety.MotorState;

import edu.wpi.first.wpilibj.GenericHID;

/**
 * An implementation for an iterative subsystem.
 * 
 * @author kyleg
 */
public abstract class Subsystem extends ControlSafety {

	private MotorState state = MotorState.STOPPED;

	private Routine routine; // The specified routine, if there's one.

	private int tick; // Seconds. Increased in 'periodic()' methods
	
	private GenericHID control;
	
	public Subsystem(GenericHID controller) {
		this.control = controller;
	}
	
	/**
	 * Runs a number of functions when required.
	 * 
	 * @param i The function. Stored usually in a switch statement in
	 *        'complete()'
	 */
	public abstract void complete(byte i);
	
	@Override
	public abstract boolean onStall();
	
	@Override
	public abstract void onStop();
	
	/**
	 * Called when the subsystem is ticked.
	 */
	public abstract void onTick();

	/**
	 * @return The subsystem's name
	 */
	public abstract String getName();

	/**
	 * @return The subsystem description
	 */
	public abstract String getDescription();

	/**
	 * Sets the routine, if the user wants one.
	 * 
	 * @param r The routine to use.
	 */
	public void setRoutine(Routine r) {
		this.routine = r;
	}

	/**
	 * Increments the subsystem time.
	 * <p>
	 * Only ticks if the subsystem's state is 'RUNNING'
	 */
	public void tick() {
		if (state == MotorState.RUNNING) {
			tick++;
			onTick();
		}
	}

	/**
	 * Resets the tick count to 0.
	 */
	public void resetTicks() {
		tick = 0;
	}

	/**
	 * @return The ticks gone by (Increments every 20ms, which equals one
	 *         second)
	 */
	public int getTicks() {
		return tick;
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
	 * 
	 * @return The subsystem's controller
	 */
	public GenericHID getController() {
		return control;
	}

}
