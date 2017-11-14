package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.subsystem.meta.AutoSubsystem;
import org.usfirst.frc.team5129.subsystem.meta.State;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;

public class Drive extends AutoSubsystem {

	private RobotDrive drive;
	private Joystick stick;
	private XboxController controller;

	private enum DriveType {
		CONTROLLER, JOYSTICK, AUTO, DUAL, UNKNOWN;
	}

	private DriveType driveType;

	public Drive(RobotDrive drive, Joystick stick) {
		super();
		this.drive = drive;
		this.stick = stick;
		this.driveType = DriveType.JOYSTICK;
	}

	public Drive(RobotDrive drive, XboxController controller) {
		super();
		this.drive = drive;
		this.controller = controller;
		this.driveType = DriveType.CONTROLLER;
	}

	public Drive(RobotDrive drive, Joystick stick, XboxController controller) {
		super();
		this.drive = drive;
		this.stick = stick;
		this.controller = controller;
		this.driveType = DriveType.DUAL;
	}

	public Drive(RobotDrive drive, GenericHID device) {
		super();
		this.drive = drive;
		this.driveType = DriveType.UNKNOWN;
		if (device instanceof Joystick) {
			this.stick = (Joystick) device;
			this.driveType = DriveType.JOYSTICK;
		} else if (device instanceof XboxController) {
			this.controller = (XboxController) device;
			this.driveType = DriveType.CONTROLLER;
		}
	}

	public Drive(RobotDrive drive) {
		super();
		this.drive = drive;
		this.driveType = DriveType.AUTO;
	}

	/**
	 * POWER=1 (Functions: [0 - Manual] [1 - Left] [2 - Right] [3 - Forward] [4
	 * - Backward])
	 */
	// TODO Adjust power output so motors don't short.
	@Override
	public void complete(int i) {
		if (getMotorState() == State.RUNNING) {
			switch (i) {
				case 0:
					decideDrive();
					break;
				case 1:
					drive.drive(1, -1);
					break;
				case 2:
					drive.drive(1, 1);
					break;
				case 3:
					drive.drive(1, 0);
					break;
				case 4:
					drive.drive(-1, 0);
			}
		} else {
			drive.stopMotor();
		}
	}

	private void decideDrive() {
		switch (driveType) {
			case CONTROLLER:
				drive.drive(controller.getY(Hand.kLeft),
						controller.getX(Hand.kLeft));
				break;
			case JOYSTICK:
				drive.arcadeDrive(stick, true);
				break;
			case DUAL:
				// TODO Add dual support.
				break;
			case AUTO:
				DriverStation.reportError(
						"DRIVETYPE=AUTO:drive_subsys_call_while_0", false);
				break;
			case UNKNOWN:
				DriverStation
						.reportError(
								"DRIVETYPE=UNKNOWN:drive_subsys_call_while_null",
								false);
				break;
			default:
				break;
		}
	}

	@Override
	public boolean done() {
		Timer.delay(0.25);
		drive.drive(0.2, 0); // Keeps the motors alive so as not to break them.
		return true;
	}

	@Override
	public String getName() {
		return "DriveTrain";
	}

	@Override
	public String getDescription() {
		return "Drives the robot around.";
	}

}
