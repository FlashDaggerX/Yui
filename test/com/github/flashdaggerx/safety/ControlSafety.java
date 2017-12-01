package com.github.flashdaggerx.safety;

/**
 * Implements safety features in a subsystem.
 * 
 * @author kyleg
 */
public abstract class ControlSafety {

	private MotorState state = MotorState.STOPPED;

	public byte killID; // The ID of the kill button.
	
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
	 * Starts the system in the 'STOP' or 'STALL' state.
	 */
	public void start() {
		if (state == MotorState.RUNNING)
			return;
		state = MotorState.RUNNING;
	}

	/**
	 * Stops the system in the 'RUNNING' state.
	 * 
	 * @see ControlSafety#onStop()
	 */
	public void stop() {
		if (state == MotorState.STOPPED || state == MotorState.STALLED)
			return;
		state = MotorState.STOPPED;
		onStop();
	}

	/**
	 * Stalls the system in the 'RUNNING' state.
	 * 
	 * @see ControlSafety#onStall()
	 */
	public void stall() {
		if (state == MotorState.STOPPED || state == MotorState.STALLED)
			return;
		state = MotorState.STALLED;
		if (onStall())
			state = MotorState.RUNNING;
	}
	
	/**
	 * @return State of the subsystem
	 */
	public MotorState getMotorState() {
		return state;
	}

}
