package org.usfirst.frc.team5129.subsystem.meta;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * An autonomous implementation of a subsystem.
 * 
 * @author kyleg
 * 
 */
public abstract class AutoSubsystem extends Subsystem {

	private Timer t;

	private int pass; // Time passed in the instruction.

	private boolean isRunning; // Is the routine running?

	public AutoSubsystem() {
		super();
		t = new Timer();
		isRunning = false;
	}

	/**
	 * Schedules an autonomous function to run. Cancels if already running.
	 * 
	 * @param r
	 *            The routine to run during autonomous.
	 * @param report
	 *            Report autonomous scheduling to the DS?
	 * @return Did scheduling the routine succeed?
	 */
	public boolean schedule(final Routine r, final boolean report) {
		if (isRunning)
			return isRunning;
		this.pass = 0;
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				r.doRoutine();
				pass++;
				if (report)
					DriverStation.reportWarning(
							("counting_auto_routine:" + pass), false);
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
	 * 
	 * @return The amount of the the routine has been running.
	 */
	public int getSeconds() {
		return pass;
	}

	@Override
	public abstract void complete(int i);

	@Override
	public abstract boolean done();

	@Override
	public abstract String getName();

	@Override
	public abstract String getDescription();
}
