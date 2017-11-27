package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.safety.MotorState;
import org.usfirst.frc.team5129.subsystem.meta.Subsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.XboxController;

public class Drive extends Subsystem {

	private enum DriveType {
		CONTROLLER, JOYSTICK;
	}

	private DriveType type = DriveType.JOYSTICK;

	private RobotDrive drive;
	private GenericHID control;

	private boolean isAuto;

	public Drive(RobotDrive drive, GenericHID control) {
		super();

		if (control instanceof Joystick)
			type = DriveType.JOYSTICK;
		else if (control instanceof XboxController)
			type = DriveType.CONTROLLER;
		
		this.drive = drive;
		this.control = control;
		this.isAuto = false;
	}

	/*
	 * Functions:
	 * 
	 * [0 - Manual] [1 - Forward] [2 - Back] [3 - Left] [4 - Right]   
	 * [5 - Swap Auto] [6 - Swap Manual]
	 */
	@Override
	public void complete(byte i) {
		if (getMotorState() == MotorState.RUNNING) {
			switch (i) {
				case 0:
					decideDrive();
					break;
				case 1:
					drive.drive(1, 0);
					break;
				case 2:
					drive.drive(-1, 0);
					break;
				case 3:
					drive.drive(0, -1);
					break;
				case 4:
					drive.drive(0, 1);
					break;
				case 5:
					isAuto = true;
					break;
				case 6:
					isAuto = false;
			}
		}
	}
	
	@Override
	public boolean onStall() {
		drive.setInvertedMotor(MotorType.kFrontLeft, true);
		drive.setInvertedMotor(MotorType.kFrontRight, true);
		drive.setInvertedMotor(MotorType.kRearLeft, true);
		drive.setInvertedMotor(MotorType.kRearRight, true);
		DriverStation.reportWarning("STATE=STALLED:drive_subsys_inverted",
				false);
		return true;
	}

	@Override
	public void onStop() {
		drive.stopMotor();
	}

	@Override
	public void onTick() {
		if (isAuto)
			getRoutine().doRoutine();
	}

	private void decideDrive() {
		switch (type) {
			case JOYSTICK:
				drive.arcadeDrive(control);
				break;
			case CONTROLLER:
				drive.drive(control.getY(Hand.kLeft), control.getX(Hand.kRight));
				break;
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
