package org.usfirst.frc.team5129.commands;

import org.usfirst.frc.team5129.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Auto extends FDCommand {
	
	DifferentialDrive drive;
	
	public Auto(Robot bot) {
		super(bot);
		
		requires(robot().getSBinder().getSubsystem(0));
		
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
		double currentTime;
		while (eval) {
			currentTime = robot().getTime();
			switch(getSIG()) {
			case SIG_1:
				if (currentTime < 3.00) {
					drive.arcadeDrive(-1, 0);
				} else {
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
