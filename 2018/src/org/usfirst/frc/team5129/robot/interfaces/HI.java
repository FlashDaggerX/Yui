package org.usfirst.frc.team5129.robot.interfaces;

import org.usfirst.frc.team5129.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.SpeedController;
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
	
	public enum DriveType {
		MECANUM, DIFFERENTIAL;
	}
	
	DriveType type;
	
	RobotDriveBase drive;
	
	Joystick stick;
	XboxController controller;
	
	public HI(DriveType type) {
		switch (type) {
			case MECANUM:
				this.type = DriveType.MECANUM;
				final SpeedController[] pwm = {
						new PWMTalonSRX(RobotMap.motors[0]), new PWMTalonSRX(RobotMap.motors[2]),
						new PWMTalonSRX(RobotMap.motors[1]), new PWMTalonSRX(RobotMap.motors[3])
				};
				this.drive = new MecanumDrive(pwm[0], pwm[1], pwm[2], pwm[3]);
				break;
			case DIFFERENTIAL:
				this.type = DriveType.DIFFERENTIAL;
				final SpeedController[] pwm_l = {
						new PWMTalonSRX(RobotMap.motors[0]), new PWMTalonSRX(RobotMap.motors[1])
				};
				this.drive = new DifferentialDrive(pwm_l[0], pwm_l[1]);
				break;
		}
		
		stick = new Joystick(RobotMap.controllers[0]);
		controller = new XboxController(RobotMap.controllers[1]);
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
}
