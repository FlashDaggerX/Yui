package org.usfirst.frc.team5129.meta;

import org.usfirst.frc.team5129.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Components handled by the driver.
 * @author kyleg
 *
 */
public abstract class Handled extends Piece {
	Joystick st;
	
	public Handled(Robot bot, Joystick st) {
		super(bot);
		this.st = st;
	}
	
	protected Joystick getCTRL() {
		return st;
	}

}
