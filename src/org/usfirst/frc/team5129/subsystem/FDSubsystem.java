package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An extension of the subsystem class, tailored to the needs of a non-static
 * brain.
 * 
 * @author kyleg
 */
public abstract class FDSubsystem extends Subsystem {
	
	Robot bot;
	
	public FDSubsystem(Robot bot) {
		this.bot = bot;
	}
	
	/**
	 * Initialize the command that this subsystem will run once it's created. The
	 * command that's created here should be running forever.
	 * <p>
	 * isFinished() should always return false.
	 */
	@Override
	protected void initDefaultCommand() {

	}
	
	public Robot robot() {
		return bot;
	}

}
