package org.usfirst.frc.team5129.commands;

import org.usfirst.frc.team5129.robot.Robot;
import org.usfirst.frc.team5129.robot.interfaces.SIG;

import edu.wpi.first.wpilibj.command.Command;

/**
 * An extension of the command class, tailored to the needs of a non-static
 * brain.
 * 
 * @author kyleg
 */
public abstract class FDCommand extends Command {

	Robot bot;
	
	SIG signal;
	
	public FDCommand(Robot bot) {
		this.bot = bot;
		this.signal = SIG.STALL;
	}
	
	@Override
	abstract public void initialize();
	
	@Override
	abstract public void execute();
	
	@Override
	abstract public void end();
	
	@Override
	abstract protected boolean isFinished();
	
	/**
	 * Send status signals to a command for different actions.
	 * @param signal The signal to send.
	 */
	public void sendSIG(SIG signal) {
		if (signal == SIG.STALL)
			cancel();
		this.signal = signal;
	}
	
	public SIG getSIG() {
		return signal;
	}
	
	protected Robot robot() {
		return bot;
	}

}
