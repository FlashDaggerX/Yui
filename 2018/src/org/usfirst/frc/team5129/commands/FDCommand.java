package org.usfirst.frc.team5129.commands;

import org.usfirst.frc.team5129.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * An extension of the command class, tailored to the needs of a non-static
 * brain.
 * 
 * @author kyleg
 */
public abstract class FDCommand extends Command {

	Robot bot;

	public FDCommand(Robot bot) {
		this.bot = bot;
	}
	
	@Override
	abstract public void initialize();
	
	@Override
	abstract public void execute();
	
	@Override
	abstract public void end();
	
	@Override
	abstract protected boolean isFinished();
	
	protected Robot robot() {
		return bot;
	}

}
