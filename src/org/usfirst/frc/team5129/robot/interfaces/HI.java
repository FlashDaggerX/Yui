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
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;

/**
 * Holds instances of the hardware. {@link RobotMap} holds the ports for the
 * hardware.
 * 
 * @author kyleg
 */
public class HI {
	
	/**
	 * Drive specifications. It creates the drive accordingly.
	 * 
	 * @author kyleg
	 */
	public enum DriveType {
		/** Four-wheel DriveType. */ MECANUM, 
		/** Two-side DriveType. */DIFFERENTIAL;
	}
	
	DriveType type;
	
	RobotDriveBase drive;
	
	Joystick stick;
	XboxController controller;
	
	UsbCamera camera;
	
	public HI(DriveType type) {
		final SpeedController[] pwm = {
				new PWMTalonSRX(RobotMap.motors[0]), new PWMTalonSRX(RobotMap.motors[2]),
				new PWMTalonSRX(RobotMap.motors[1]), new PWMTalonSRX(RobotMap.motors[3])
		};
		
		//FIXME Inverted for RALPH
		pwm[0].setInverted(true);
		pwm[1].setInverted(true);
		
		switch (type) {
			// FIXME Fix port assignments accordingly
			case MECANUM:
				this.type = DriveType.MECANUM;
				this.drive = new MecanumDrive(pwm[0], pwm[1], pwm[2], pwm[3]);
				break;
			case DIFFERENTIAL:
				this.type = DriveType.DIFFERENTIAL;
				final SpeedControllerGroup[] grp = {
						new SpeedControllerGroup(pwm[0], pwm[1]),
						new SpeedControllerGroup(pwm[2], pwm[3])
				};
				this.drive = new DifferentialDrive(grp[0], grp[1]);
				break;
		}
		
		stick = new Joystick(RobotMap.controllers[0]);
		controller = new XboxController(RobotMap.controllers[1]);
		
		camera = CameraServer.getInstance().startAutomaticCapture();
	}
	
	public RobotDriveBase getDrive() {
		if (type == DriveType.MECANUM) {
			return (MecanumDrive) drive;
		} else {
			return (DifferentialDrive) drive;
		}
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
