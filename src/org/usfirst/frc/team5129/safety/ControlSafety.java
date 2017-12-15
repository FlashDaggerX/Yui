package org.usfirst.frc.team5129.safety;

/**
 * Implements safety features in a subsystem.
 * 
 * @author kyleg
 */
public abstract class ControlSafety {

	private MotorState state = MotorState.STOPPED;

	/**
	 * Called when the state is changed to 'STOPPED'
	 * 
	 * @return Did the operation complete?
	 */
	public abstract void onStop();

	/**
	 * Starts the system in the 'STOP' or 'STALL' state.
	 */
	public void start() {
		state = MotorState.RUNNING;
	}

	/**
	 * Stops the system in the 'RUNNING' state.
	 * 
	 * @see ControlSafety#onStop()
	 */
	public void stop() {
		state = MotorState.STOPPED;
		onStop();
	}

	/**
	 * @return State of the subsystem
	 */
	public MotorState getMotorState() {
		return state;
	}
}
