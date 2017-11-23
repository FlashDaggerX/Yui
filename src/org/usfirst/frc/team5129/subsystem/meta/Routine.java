package org.usfirst.frc.team5129.subsystem.meta;

/**
 * Describes a routine in an AutoSubsystem.
 * 
 * @author kyleg
 * @see AutoSubsystem
 */
@FunctionalInterface
public interface Routine {

	/**
	 * Executes different custom routines during scheduling. For example, in
	 * autonomous. Or in live subsystems, when a custom instruction needs to be
	 * run.
	 */
	public void doRoutine();
}