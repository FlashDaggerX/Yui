package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.robot.Robot;

public class Camera extends FDSubsystem {

	public Camera(Robot bot) {
		super(bot);
		
		setSubsystem("sCamera");
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(robot().getCommandBinder().getCameraCommands()[0]);
	}
	
}
