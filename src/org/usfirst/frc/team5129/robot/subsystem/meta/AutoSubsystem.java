package org.usfirst.frc.team5129.robot.subsystem.meta;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An autonomous implementation of a subsystem.
 * @author kyleg
 *
 */
public abstract class AutoSubsystem extends Subsystem {
	
	private Timer t;
	
	public AutoSubsystem() {
		super();
		t = new Timer();
	}
	
	/**
	 * Schedules an autonomous function to run. Calls 'complete(0)'
	 * @param length The length, in seconds, of the autonomous.
	 */
	public void schedule(int length) {
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				complete(0);
			}
			
		}, 0, length);
	}
	
	/**
	 * Always called at '0' in auto.
	 */
	@Override
	public abstract void complete(int i);

	@Override
	public abstract boolean done();

	@Override
	public abstract String getName();

	@Override
	public abstract String getDescription();
}
