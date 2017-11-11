package org.usfirst.frc.team5129.robot.subsystem.meta;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An autonomous implementation of a subsystem.
 * 
 * @author kyleg
 * 
 */
public abstract class AutoSubsystem extends Subsystem {

	private Timer t;

	private int pass; // Time passed in the instruction.
	private int length; // Length of the autonomous instruction.

	private boolean isRunning; // Is the routine running?

	public AutoSubsystem() {
		super();
		t = new Timer();
		isRunning = false;
	}

	/**
	 * Schedules an autonomous function to run. Calls 'complete(0)'. Cancels if
	 * already running.
	 * 
	 * @param l
	 *            The length, in seconds, of the autonomous.
	 * @return Did scheduling the routine succeed?
	 */
	public boolean schedule(int l) {
		if (isRunning)
			return isRunning;
		this.length = l;
		this.pass = 0;
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				complete(0);
				pass++;

				if (pass == length)
					this.cancel();
			}

		}, 0, 1000);
		isRunning = true;
		return isRunning;
	}

	/**
	 * Cancels the autonomous routine.
	 */
	public void breakRoutine() {
		t.cancel();
		isRunning = false;
	}

	/**
	 * 
	 * @return Is the autonomous routine running?
	 */
	public boolean isRunning() {
		return isRunning;
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
