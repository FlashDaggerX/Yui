package org.usfirst.frc.team5129.robot.subsystem;

import org.usfirst.frc.team5129.robot.subsystem.meta.State;
import org.usfirst.frc.team5129.robot.subsystem.meta.Subsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drive extends Subsystem {

	private RobotDrive drive;
	private Joystick stick;

	public Drive(RobotDrive drive, Joystick stick) {
		super();
		this.drive = drive;
		this.stick = stick;
	}

	public Drive(RobotDrive drive) {
		super();
		this.drive = drive;
	}

	/**
	 * Power: 1 **DO NOT USE** (Functions: [0 - Arcade] [1 - Left] [2 - Right])
	 */
	// TODO Adjust power output so motors don't short.
	@Override
	public void complete(int i) {
		if (getMotorState() == State.RUNNING) {
			switch (i) {
				case 0:
					if (stick != null)
						drive.arcadeDrive(stick, true);
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

	@Override
	public boolean done() {
		return false;
	}

	@Override
	public String getName() {
		return "DriveTrain";
	}

	@Override
	public String getDescription() {
		return "Drives the robot forward.";
	}

}
