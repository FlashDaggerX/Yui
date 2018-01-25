package org.usfirst.frc.team5129.robot.interfaces;

import org.usfirst.frc.team5129.robot.RobotMap;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Holds instances of the hardware. {@link RobotMap} holds the ports for the
 * hardware.
 * 
 * @author kyleg
 */
public class HI {
	
	DifferentialDrive drive;
	
	Joystick stick;
	XboxController controller;
	
	UsbCamera camera;
	
	public HI() {
		final SpeedController[] pwm = {
				new PWMTalonSRX(RobotMap.motors[0]), new PWMTalonSRX(RobotMap.motors[2]),
				new PWMTalonSRX(RobotMap.motors[1]), new PWMTalonSRX(RobotMap.motors[3])
		};
		
		//FIXME Inverted for RALPH
		pwm[0].setInverted(true);
		pwm[1].setInverted(true);
		
		final SpeedControllerGroup[] grp = {
				new SpeedControllerGroup(pwm[0], pwm[1]),
				new SpeedControllerGroup(pwm[2], pwm[3])
		};
		this.drive = new DifferentialDrive(grp[0], grp[1]);
		
		stick = new Joystick(RobotMap.controllers[0]);
		controller = new XboxController(RobotMap.controllers[1]);
		
		camera = CameraServer.getInstance().startAutomaticCapture();
	}
	
	public DifferentialDrive getDrive() {
		return drive;
	}

	public Joystick getStick() {
		return stick;
	}

	public XboxController getController() {
		return controller;
	}
	
	public UsbCamera getCameraServer() {
		return camera;
	}
}
