package org.usfirst.frc.team5129.commands;

import org.usfirst.frc.team5129.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;

public class Auto extends FDCommand {
	
	public enum AutoType {
		DISABLED, T1;
	}
	
	AutoType type = AutoType.DISABLED;
	
	RobotDriveBase drive;
	
	public Auto(Robot bot, AutoType type) {
		super(bot);
		requires(robot().getSBinder().getSubsystem(0));
		
		this.type = type;
		
		setName("Auto");
		setSubsystem("sDrive");
	}

	@Override
	public void initialize() {
		this.drive = robot().getHBinder().getDrive();
	}
	
	@Override
	public void execute() {
		boolean eval = robot().isAutonomous() && robot().isEnabled();
		while (eval) {
			switch(type) {
				case DISABLED:
					DriverStation.reportError("STATE=Auto:running_auto_in_disabled", false);
					break;
				case T1:
					double currentTime = robot().getTime();
					if (currentTime == 0.50) {
						((DifferentialDrive) drive).arcadeDrive(0.5, 0);
					} else if (currentTime == 1.0) {
						drive.stopMotor();
					}
					break;
				default:
					DriverStation.reportError("STATE=Auto:running_auto_in_disabled", false);
					break;
			}
		}
	}
	
	@Override
	public void end() {
		
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
}
