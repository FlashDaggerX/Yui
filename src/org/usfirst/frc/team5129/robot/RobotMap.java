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
	 * 
	 * 
	 * 
	 * Yui:
	 * 
	 * Drive (2,3,1,0) Inverted Right
	 */

	public RobotMap() {

	}

	/**
	 * Describes the four motor ports. They go in this order:
	 * 
	 * frontLeft, rearLeft, frontRight, rearRight
	 */
	//	 * orig 1, 0, 2, 3
	public int[] motors = { 2, 3, 1, 0 };
	
	/**
	 * Describes motor inverts:
	 * 
	 * Left, Right
	 * 
	 * @see RobotMap#motors
	 */
	public boolean[] invert = { false, true };
	

	/**
	 * Describes other components. They go in this order:
	 * 
	 * Claw, Arm, Wench, Scissor 
	 */
	public int[] components = { 9, 8, 7, 6 };

	/**
	 * Describes any controller ports. They go in this order:
	 * 
	 * Joystick, Controller
	 */
	public int[] controllers = { 0, 1 };
}
