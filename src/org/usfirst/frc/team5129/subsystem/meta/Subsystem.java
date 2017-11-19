package org.usfirst.frc.team5129.subsystem.meta;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * An implementation for an iterative subsystem.
 * 
 * @author kyleg
 * 
 */
public abstract class Subsystem {

	private State state = State.STOPPED;

	private volatile int tick; // Seconds. Increased in 'periodic()' methods

	/**
	 * Starts the system in the 'STOP' or 'STALL' state.
	 */
	public void start() {
		if (state == State.RUNNING)
			return;
		state = State.RUNNING;
	}

	/**
	 * Stops the system in the 'RUNNING' state.
	 */
	public void stop() {
		if (state == State.STOPPED || state == State.STALLED)
			return;
		state = State.STOPPED;
		onStop();
	}

	/**
	 * Stalls the system in the 'RUNNING' state.
	 * 
	 * Usually used to perform function mid-run.
	 */
	public void stall() {
		if (state == State.STOPPED || state == State.STALLED)
			return;
		state = State.STALLED;
		if (onStall())
			state = State.RUNNING;
		else
			DriverStation.reportError(
					"STATE=STALLED:subsys_returned_false_loop", true);
	}

	/**
	 * Increments the subsystem time. Only ticks if the subsystem's state is
	 * 'RUNNING'
	 */
	public synchronized void tick() {
		if (state == State.RUNNING) {
			tick++;
			onTick();
		}
	}

	/**
	 * Resets the tick count to 0.
	 */
	public synchronized void resetTicks() {
		tick = 0;
	}

	/**
	 * 
	 * @return The ticks gone by (Increments every 20ms, which equals one
	 *         second)
	 */
	public synchronized int getTicks() {
		return tick;
	}

	/**
	 * 
	 * @return State of the subsystem
	 */
	public State getMotorState() {
		return state;
	}

	/**
	 * Runs a number of functions when required.
	 * 
	 * @param i
	 *            The function. Stored usually in a switch statement in
	 *            'complete()'
	 * @param r
	 *            The routine to run when a custom instruction is called.
	 *            Specified by 'i'. This can be left null otherwise.
	 */
	public abstract void complete(int i, final Routine r);

	/**
	 * Called when the subsystem is ticked.
	 */
	public abstract void onTick();

	/**
	 * Called when the state is changed to 'STALLED'
	 * 
	 * @return Did the operation complete?
	 */
	public abstract boolean onStall();

	/**
	 * Called when the state is changed to 'STOPPED'
	 * 
	 * @return Did the operation complete?
	 */
	public abstract void onStop();

	/**
	 * 
	 * @return The subsystem's name
	 */
	public abstract String getName();

	/**
	 * 
	 * @return The subsystem description
	 */
	public abstract String getDescription();
}
