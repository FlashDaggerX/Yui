package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.safety.MotorState;
import org.usfirst.frc.team5129.subsystem.meta.Subsystem;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.XboxController;

public class Drive extends Subsystem {

	enum DriveType {
		CONTROLLER, JOYSTICK;
	}

	private DriveType type = DriveType.CONTROLLER;

	private RobotDrive drive;

	public Drive(GenericHID controller, RobotDrive drive) {
		super(controller);

		if (controller instanceof Joystick) {
			type = DriveType.JOYSTICK;
		} else if (controller instanceof XboxController) {
			type = DriveType.CONTROLLER;
		}

		this.drive = drive;
	}

	@Override
	public void complete(byte i) {
		if (getMotorState() == MotorState.RUNNING) {
			switch (i) {
				case 10:
					decideDrive();
					break;
				case 20:
					drive.drive(1, 0);
				case 50:
					for (MotorType m : RobotDrive.MotorType.values()) {
						drive.setInvertedMotor(m, true);
					}
					break;
			}
		}
	}

	private void decideDrive() {
		switch (type) {
			case JOYSTICK:
				drive.arcadeDrive(getController());
				break;
			case CONTROLLER:
				drive.drive(getController().getY(Hand.kLeft), getController()
						.getX(Hand.kLeft));
				break;
		}
	}

	@Override
	public byte getID() {
		return 10;
	}

}
