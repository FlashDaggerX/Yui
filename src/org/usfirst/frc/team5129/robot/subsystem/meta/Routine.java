package org.usfirst.frc.team5129.robot.subsystem.meta;

/**
 * Describes a routine in an AutoSubsystem.
 * @author kyleg
 * @see AutoSubsystem
 */
@FunctionalInterface
public interface Routine {
	
	/**
	 * Executes the routine during scheduling.
	 */
	public void doRoutine();
}
