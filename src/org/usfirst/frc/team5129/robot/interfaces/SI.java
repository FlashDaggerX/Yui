package org.usfirst.frc.team5129.robot.interfaces;

import org.usfirst.frc.team5129.robot.Robot;
import org.usfirst.frc.team5129.subsystem.Drive;
import org.usfirst.frc.team5129.subsystem.FDSubsystem;

/**
 * Contains subsystem instances. Used for keeping the subs together in an
 * object, rather than keeping them static. It's also worth noting that marking
 * dependencies this way is less error-prone.
 * 
 * @author kyleg
 */
public class SI {

	Robot bot;

	FDSubsystem[] subs;

	public SI(Robot bot) {
		this.bot = bot;

		subs = new FDSubsystem[] { new Drive(bot) };
	}

	public FDSubsystem[] getSubsystems() {
		return subs;
	}

}
