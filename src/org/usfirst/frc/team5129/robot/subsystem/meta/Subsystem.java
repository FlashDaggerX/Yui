package org.usfirst.frc.team5129.robot.subsystem.meta;

/**
 * An implementation for an iterative subsystem.
 * 
 * @author kyleg
 * 
 */
public abstract class Subsystem {

	private State state = State.STOPPED;
	
	public int functionCount = 0;
	
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
		if (done())
			state = State.RUNNING;
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
	 *            The function. Stored usually in a nested if-else statement in
	 *            'complete()'
	 */
	public abstract void complete(int i);

	/**
	 * Called when the state is changed to 'STALLED'
	 * 
	 * @return Did the operation complete?
	 */
	public abstract boolean done();

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
