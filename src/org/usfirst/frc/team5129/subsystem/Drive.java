package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.subsystem.meta.AutoSubsystem;
import org.usfirst.frc.team5129.subsystem.meta.Routine;
import org.usfirst.frc.team5129.subsystem.meta.State;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.XboxController;

public class Drive extends AutoSubsystem {

	private enum DriveType {
		AUTO, CONTROLLER, JOYSTICK;
	}

	private DriveType type = DriveType.AUTO;

	private RobotDrive drive;
	private GenericHID control;

	public Drive(RobotDrive drive, GenericHID control) {
		this.drive = drive;

		if (control instanceof Joystick)
			type = DriveType.JOYSTICK;
		else if (control instanceof XboxController)
			type = DriveType.CONTROLLER;

		if (control == null)
			type = DriveType.AUTO;

		this.control = control;
	}
	
	/*
	 * Functions:
	 * 
	 * [0 - Manual] [1 - Routine]
	 * [2 - Forward] [3 - Back]
	 * [4 - Right] [5 - Left]
	 */
	@Override
	public void complete(int i, Routine r) {
		if (getMotorState() == State.RUNNING) {
			switch (i) {
				case 0:
					decideDrive();
					break;
				case 1:
					decideDrive();
					change();
					break;
				case 2:
					r.doRoutine();
					break;
				case 3:
					if (type == DriveType.AUTO)
						drive.drive(getPower(), getCurve());
					break;
			}
		}
	}

	@Override
	public void onTick() {

	}

	private void decideDrive() {
		switch (type) {
			case JOYSTICK:
				drive.arcadeDrive(control);
				break;
			case CONTROLLER:
				drive.drive(control.getY(Hand.kLeft), control.getX(Hand.kRight));
				break;
			default:
				DriverStation.reportError(
						"STATE=RUNNING:drive_subsys_drivetype_unknown", false);
				break;
		}
	}

	@Override
	public boolean onStall() {
		return true;
	}
	
	@Override
	public void onStop() {
		drive.stopMotor();
	}
	
	@Override
	public void onKill() {
		stop();
	}
	
	private void change() {
		if (control.getRawButton(buttonID)) {
			kill();
		}
	}

	@Override
	public String getName() {
		return "DriveTrain";
	}

	@Override
	public String getDescription() {
		return "Controls the robot's movements.";
	}
}
