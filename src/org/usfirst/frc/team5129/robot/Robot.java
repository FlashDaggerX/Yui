package org.usfirst.frc.team5129.robot;

import org.usfirst.frc.team5129.subsystem.Camera;
import org.usfirst.frc.team5129.subsystem.Drive;
import org.usfirst.frc.team5129.subsystem.Lift;
import org.usfirst.frc.team5129.subsystem.meta.Routine;
import org.usfirst.frc.team5129.subsystem.meta.Subsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Robot extends IterativeRobot {

	private SendableChooser<Integer> choice;

	private RobotDrive drive;
	@SuppressWarnings("unused")
	private Joystick stick;
	private XboxController controller;

	private Spark lift;

	private Subsystem[] subs;

	@Override
	public void robotInit() {
		choice = new SendableChooser<>();
		choice.initTable(NetworkTable.getTable("Yui"));
		choice.getTable().putString("autonomous_routine", "0");

		OI oi = new OI();

		drive = new RobotDrive(oi.motors[0], oi.motors[1], oi.motors[2],
				oi.motors[3]);
		
		stick = new Joystick(oi.controllers[0]);
		
		controller = new XboxController(oi.controllers[1]);
		
		lift = new Spark(oi.components[0]);

		subs = new Subsystem[] { new Drive(drive, controller), new Lift(lift),
				new Camera() };

	}

	@Override
	public void autonomousInit() {
		for (Subsystem s : subs) {
			s.start();
		}

		int choose = Integer.parseInt(choice.getTable().getString(
				"autonomous_routine",
				"0"));

		switch (choose) {
			case 0:
				subs[0].setRoutine(new Routine() {
					
					@Override
					public void doRoutine() {
						if (subs[0].getTicks() == 0) {
							subs[0].complete((byte) 2);
						}
					}
				});
				subs[0].complete((byte) 5);
				break;
		}
	}

	@Override
	public void autonomousPeriodic() {
		subs[0].tick();
	}

	@Override
	public void teleopInit() {
		subs[0].complete((byte) 6);
		for (Subsystem s : subs) {
			s.resetTicks();
			s.start();
		}
	}

	@Override
	public void teleopPeriodic() {
		for (Subsystem s : subs) {
			s.tick();

			if (!s.getName().equalsIgnoreCase("CameraServer"))
				s.complete((byte) 0);
		}
	}

	@Override
	public void disabledInit() {
		for (Subsystem s : subs) {
			s.resetTicks();
			s.stop();
		}
	}
}
