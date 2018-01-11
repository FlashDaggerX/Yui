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
		requires(robot().getSBinder().getSubsystem(0));
		
		setName("Move");
		setSubsystem("sDrive");
	}
	
	@Override
	public void initialize() {
		drive = robot().getHBinder().getDrive();
		
		stick = robot().getHBinder().getStick();
	}
	
	@Override
	public void execute() {
		double x = stick.getX();
		double y = stick.getY();
		((DifferentialDrive) drive).arcadeDrive(x, y, true);
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
