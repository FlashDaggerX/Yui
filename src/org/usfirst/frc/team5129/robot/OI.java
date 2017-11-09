package org.usfirst.frc.team5129.robot;

/**
 * Port bindings.
 * @author kyleg
 *
 */
public class OI {
	
	public OI() {
		
	}
	
	/**
	 * Describes the four motor ports. They go in this order:
	 * 
	 * RearLeft, RearRight, FrontLeft, FrontRight
	 */
	public int[] motors = 
		{
			0,
			1,
			2,
			3
		};
	
	/**
	 * Describes any controller ports. They go in this order:
	 * 
	 * Joystick, Controller
	 */
	public int[] controllers = 
		{
			7,
			9
		};
}
