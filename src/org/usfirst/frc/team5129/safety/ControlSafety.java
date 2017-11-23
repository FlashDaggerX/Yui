package org.usfirst.frc.team5129.safety;

/**
 * Used in Test Mode, when killing the robot is typically
 * needed.
 * 
 * <p> You could also consider it a first layer before the
 * stop command.
 * 
 * @author kyleg
 *
 */
public abstract class ControlSafety {
	
	private SafeState type = SafeState.OK;
	
	public byte buttonID; // The ID of the kill button.
	
	/**
	 * Starts or Resumes motor progress.
	 */
	public void resume() {
		type = SafeState.OK;
	}
	
	/**
	 * Kill the motor entirely, regardless of the task.
	 */
	public void kill() {
		type = SafeState.EMERGENCY;
		onKill();
	}
	
	public void setKillButton(byte id) {
		this.buttonID = id;
	}
	
	/**
	 * Called when the motor is killed.
	 */
	public abstract void onKill();
	
	/**
	 * 
	 * @return The state of the motor safety feature.
	 */
	public SafeState getSafetyState() {
		return type;
	}
}
