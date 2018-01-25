package org.usfirst.frc.team5129.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	/**
	 * Array data for ports.
	 * <p>
	 * Motors: [0] fLeft; [1] fRight; [2] rLeft; [3] rRight; [4] Claw
	 * <p>
	 * Controllers: [0] Joystick; [1] XBoxController
	 */
	public static int[] motors, controllers;
	
	// TODO Fix ports
	public RobotMap() {
		motors = new int[] {
				1,
				2,
				3,
				4,
				8
		};

		controllers = new int[] {
				0,
				1
		};
	}
}
