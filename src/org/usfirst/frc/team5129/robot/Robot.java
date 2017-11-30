package org.usfirst.frc.team5129.robot;

import org.usfirst.frc.team5129.subsystem.Drive;
import org.usfirst.frc.team5129.subsystem.meta.Subsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Robot extends IterativeRobot {
	
	private int time;
	
	private RobotDrive drive;
	private Joystick stick;
//	private XboxController controller;
//
//	private Spark lift;
//	private Spark collect;

	private Subsystem[] subs;

	@Override
	public void robotInit() {
		time = 0;
		
		OI oi = new OI();

		drive = new RobotDrive(oi.motors[0], oi.motors[1], oi.motors[2],
				oi.motors[3]);

		stick = new Joystick(oi.controllers[0]);
//		controller = new XboxController(oi.controllers[1]);
//
//		lift = new Spark(oi.components[0]);
//		collect = new Spark(oi.components[1]);

		subs = new Subsystem[] {
				new Drive(stick, drive)
		};
	}
	
	@Override
	public void robotPeriodic() {
		time++;
	}

	@Override
	public void autonomousInit() {
		time = 0;
	}

	@Override
	public void autonomousPeriodic() {
		if (time == 0)
			subs[0].getRoutine().doRoutine();
	}

	@Override
	public void teleopInit() {
		time = 0;
	}

	@Override
	public void teleopPeriodic() {
		while (isOperatorControl()) {
			subs[0].complete((byte) 100);
		}
	}

	@Override
	public void disabledInit() {
		time = 0;
	}
}
