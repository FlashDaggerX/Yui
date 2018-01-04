package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.robot.Robot;

public class Drive extends FDSubsystem {

	public Drive(Robot bot) {
		super(bot);
		
		setName("Drive");
		setSubsystem("sDrive");
	}
	
	@Override
	protected void initDefaultCommand() {
		robot().getCommandBinder().getDriveCommands()[0].start();
	}

}
