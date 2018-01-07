package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.robot.Robot;

public class Drive extends FDSubsystem {

	public Drive(Robot bot) {
		super(bot);
		
		setSubsystem("sDrive");
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(robot().getCommandBinder().getDriveCommands()[0]);
	}

}
