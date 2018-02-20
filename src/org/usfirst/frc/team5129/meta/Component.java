package org.usfirst.frc.team5129.meta;

import org.usfirst.frc.team5129.robot.Robot;

import edu.wpi.first.wpilibj.XboxController;

/**
 * A component of the bot, controlled by the Xbox Controller, aside
 * the driver.
 * 
 * @author kyleg
 *
 */
public abstract class Component extends Piece {
	XboxController ct;
	
	public Component(Robot bot, XboxController ct) {
		super(bot);
		this.ct = ct;
	}
	
	protected XboxController getCTRL() {
		return ct;
	}

}
