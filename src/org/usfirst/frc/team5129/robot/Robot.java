package org.usfirst.frc.team5129.robot;

import org.usfirst.frc.team5129.robot.subsystem.Drive;
import org.usfirst.frc.team5129.robot.subsystem.meta.AutoSubsystem;
import org.usfirst.frc.team5129.robot.subsystem.meta.Subsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends IterativeRobot {

	private RobotDrive drive;
	private Joystick stick;
	@SuppressWarnings("unused")
	private XboxController controller;

	private Subsystem[] subs;
	private AutoSubsystem[] auto;

	@Override
	public void robotInit() {
		OI oi = new OI();

		drive = new RobotDrive(oi.motors[0], oi.motors[1], oi.motors[2],
				oi.motors[3]);

		stick = new Joystick(oi.controllers[0]);
		controller = new XboxController(oi.controllers[1]);

		subs = new Subsystem[1];
		subs[0] = new Drive(drive, stick);

		auto = new AutoSubsystem[1];
		subs[0] = new Drive(drive);
	}

	@Override
	public void autonomousInit() {
		int autoChoice = 0; // TODO Create Dashboard for this.
		switch (autoChoice) {
			case 0:
				auto[0].schedule(5);
				break;
		}
	}

	@Override
	public void autonomousPeriodic() {
		updateDash();
	}

	@Override
	public void teleopPeriodic() {
		updateMotor();
		updateDash();
	}

	@Override
	public void testPeriodic() {
		updateMotor();
		updateDash();
	}

	private void updateMotor() {
		// Placeholder
	}

	private void updateDash() {
		// Placeholder
	}
}
