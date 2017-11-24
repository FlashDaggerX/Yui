package org.usfirst.frc.team5129.robot;

import org.usfirst.frc.team5129.subsystem.Camera;
import org.usfirst.frc.team5129.subsystem.Drive;
import org.usfirst.frc.team5129.subsystem.Lift;
import org.usfirst.frc.team5129.subsystem.meta.AutoSubsystem;
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
	private AutoSubsystem[] auto;
	private Routine routine;

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

		subs = new Subsystem[] { new Drive(drive, controller), new Camera(),
				new Lift(lift) };

		auto = new AutoSubsystem[] { new Drive(drive, null) };
	}

	@Override
	public void autonomousInit() {
		int choose = Integer.parseInt(choice.getTable().getString(
				"autonomous_routine", "0"));
		subs[1].start();
		subs[1].complete(0, null);
		switch (choose) {
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

	@Override
	public void autonomousPeriodic() {
		for (AutoSubsystem s : auto) {
			s.tick();
		}
		switch (0) {
			case 0:
				auto[0].complete(3, routine);
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
			if (!s.getName().equalsIgnoreCase("CameraServer"))
				s.complete(0, null);
		}
	}

	@Override
	public void disabledInit() {
		for (Subsystem s : subs) {
			s.kill();
		}
		for (AutoSubsystem s : auto) {
			s.kill();
		}
	}
}
