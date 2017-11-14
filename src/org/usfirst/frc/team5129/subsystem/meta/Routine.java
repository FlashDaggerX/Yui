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
	 * Executes the routine during scheduling. Handy to do time checks with the
	 * autonomous timer.
	 * 
	 * For example, use {@code AutoSubsystem.getSeconds()} to execute different
	 * instructions. Always end a routine with
	 * {@code AutoSubsystem.breakRoutine()}.
	 * 
	 * @see AutoSubsystem
	 */
	public void doRoutine();
}
