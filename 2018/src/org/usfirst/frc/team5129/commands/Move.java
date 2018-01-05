package org.usfirst.frc.team5129.commands;

import org.usfirst.frc.team5129.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;

public class Move extends FDCommand {
	
	RobotDriveBase drive;
	
	Joystick stick;
	
	public Move(Robot bot) {
		super(bot);
		requires(robot().getSubsystemBinder().getSubsystems()[0]);
		
		setName("Move");
		setSubsystem("sDrive");
	}

	@Override
	public void initialize() {
		drive = robot().getHardwareBinder().getDrive();
		
		stick = robot().getHardwareBinder().getStick();
	}

	@Override
	public void execute() {
		((DifferentialDrive) drive).arcadeDrive(stick.getX(), stick.getZ(), true);
	}

	@Override
	public void end() {
		drive.stopMotor();
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
