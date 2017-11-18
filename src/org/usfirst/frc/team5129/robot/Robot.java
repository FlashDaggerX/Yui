package org.usfirst.frc.team5129.robot;

import org.usfirst.frc.team5129.subsystem.Camera;
import org.usfirst.frc.team5129.subsystem.Drive;
import org.usfirst.frc.team5129.subsystem.meta.AutoSubsystem;
import org.usfirst.frc.team5129.subsystem.meta.Routine;
import org.usfirst.frc.team5129.subsystem.meta.Subsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Robot extends IterativeRobot {

	private SendableChooser<String> choice;

	private RobotDrive drive;
	@SuppressWarnings("unused")
	private Joystick stick;
	private XboxController controller;

	private Subsystem[] subs;
	private AutoSubsystem[] auto;
	private Routine routine;

	@SuppressWarnings("deprecation")
	@Override
	public void robotInit() {
		choice = new SendableChooser<>();
		choice.initTable(NetworkTable.getTable("Yui"));
		choice.getTable().putInt("autonomous_routine", 0);

		OI oi = new OI();

		drive = new RobotDrive(oi.motors[0], oi.motors[1], oi.motors[2],
				oi.motors[3]);

		stick = new Joystick(oi.controllers[0]);
		controller = new XboxController(oi.controllers[1]);

		subs = new Subsystem[1];
		subs[0] = new Drive(drive, controller);
		subs[1] = new Camera();

		auto = new AutoSubsystem[1];
		auto[0] = new Drive(drive, null);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void autonomousInit() {
		subs[1].start();
		subs[1].complete(0, null);
		switch (choice.getTable().getInt("autonomous_routine")) {
			case 0:
				auto[0].start();
				routine = new Routine() {
					@Override
					public void doRoutine() {
						if (auto[0].getTicks() == 0) {
							auto[0].setPower(1);
							auto[0].setCurve(0);
							auto[0].complete(3, null);
						}
					}
				};
				break;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void autonomousPeriodic() {
		for (AutoSubsystem s : auto) {
			s.tick();
		}
		switch (choice.getTable().getInt("autonomous_routine")) {
			case 0:
				auto[0].complete(2, routine);
				break;
		}
	}

	@Override
	public void teleopInit() {
		for (AutoSubsystem s : auto) {
			s.stop();
		}
		for (Subsystem s : subs) {
			s.start();
		}
	}

	@Override
	public void teleopPeriodic() {
		for (Subsystem s : subs) {
			s.tick();
		}
	}

	@Override
	public void disabledInit() {
		for (Subsystem s : subs) {
			s.stop();
		}
		for (AutoSubsystem s : auto) {
			s.stop();
		}
	}
}
