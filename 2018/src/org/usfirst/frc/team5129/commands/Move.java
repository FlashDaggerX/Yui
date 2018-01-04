package org.usfirst.frc.team5129.commands;

import org.usfirst.frc.team5129.robot.Robot;

public class Move extends FDCommand {

	public Move(Robot bot) {
		super(bot);
		requires(robot().getSubsystemBinder().getSubsystems()[0]);
	}

	@Override
	public void initialize() {
		
	}

	@Override
	public void execute() {
		
	}

	@Override
	public void end() {
		
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
