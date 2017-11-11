package org.usfirst.frc.team5129.robot.subsystem;

import org.usfirst.frc.team5129.robot.subsystem.meta.State;
import org.usfirst.frc.team5129.robot.subsystem.meta.Subsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.XboxController;

public class Drive extends Subsystem {

	private RobotDrive drive;
	private Joystick stick;
	@SuppressWarnings("unused")
	private XboxController controller;
	
	private enum DriveType {
		CONTROLLER, JOYSTICK, AUTO;
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

	public Drive(RobotDrive drive) {
		super();
		this.drive = drive;
		this.driveType = DriveType.AUTO;
	}
	
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
			}
		} else {
			drive.stopMotor();
		}
	}
	
	private void decideDrive() {
		switch (driveType) {
			case AUTO:
				break;
			case CONTROLLER:
				// drive.arcadeDrive(controller, squaredInputs)
				// TODO Add Controller Support
				break;
			case JOYSTICK:
				drive.arcadeDrive(stick, true);
				break;
		}
	}

	@Override
	public boolean done() {
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
