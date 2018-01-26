package org.usfirst.frc.team5129.robot;

/**
 * Port bindings.
 * 
 * @author kyleg
 * 
 */
public class RobotMap {

	/*
	 * Ralph:
	 * 
	 * Drive (1,0,2,3) Inverted Lift (8) Collect (9)
	 * 
	 * Joystick (0) XboxController (1)
	 */

	public RobotMap() {

	}

	/**
	 * Describes the four motor ports. They go in this order:
	 * 
	 * frontLeft, rearLeft, frontRight, rearRight
	 */
	public int[] motors = { 1, 0, 2, 3 };

	/**
	 * Describes other components. They go in this order:
	 * 
	 * Lift (SPARK Controller), Collect (SPARK Controller)
	 */
	public int[] components = { 8, 9 };

	/**
	 * Describes any controller ports. They go in this order:
	 * 
	 * Joystick, Controller
	 */
	public int[] controllers = { 0, 1 };
}