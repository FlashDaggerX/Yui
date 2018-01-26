package org.usfirst.frc.team5129.meta;

/**
 * Describes a routine in a Subsystem.
 * 
 * @author kyleg
 * @see Subsystem
 */
@FunctionalInterface
public interface Routine {

	/**
	 * Executes different custom routines during scheduling.
	 * <p>
	 * For example, in autonomous. Or in live subsystems, when a custom instruction
	 * needs to be run.
	 */
	public void doRoutine();
}
