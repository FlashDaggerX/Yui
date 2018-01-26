package org.usfirst.frc.team5129.robot;

import org.usfirst.frc.team5129.meta.Subsystem;
import org.usfirst.frc.team5129.subsystem.Camera;
import org.usfirst.frc.team5129.subsystem.Claw;
import org.usfirst.frc.team5129.subsystem.Drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends TimedRobot {

	DifferentialDrive drive;

	Spark claw;

	Joystick stick;
	XboxController controller;

	Subsystem[] subs;

	@Override
	public void robotInit() {
		setPeriod(0.05);

		init(false);

		subs[0].complete(0x0);

		subs[1].complete(0x1a);

		subs[2].complete(0x1a);
	}

	@Override
	public void robotPeriodic() {
		for (Subsystem s : subs) {
			s.tick();
		}
	}

	@Override
	public void autonomousInit() {
		for (Subsystem s : subs) {
			s.resetTicks();
			s.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		while (isEnabled()) {
			subs[1].getRoutine().doRoutine();
		}
	}

	@Override
	public void teleopInit() {
		for (Subsystem s : subs) {
			s.resetTicks();
			s.start();
		}
	}

	@Override
	public void teleopPeriodic() {
		
	}

	@Override
	public void disabledInit() {
		for (Subsystem s : subs) {
			s.stop();
		}
	}

	void init(boolean invert) {
		RobotMap oi = new RobotMap();

		final PWMTalonSRX[] pwm = { new PWMTalonSRX(oi.motors[0]), new PWMTalonSRX(oi.motors[1]),
				new PWMTalonSRX(oi.motors[2]), new PWMTalonSRX(oi.motors[3]) };

		final SpeedControllerGroup[] grp = { new SpeedControllerGroup(pwm[0], pwm[1]),
				new SpeedControllerGroup(pwm[2], pwm[3]) };

		if (invert) {
			grp[0].setInverted(true);
			grp[1].setInverted(true);
		}

		drive = new DifferentialDrive(grp[0], grp[1]);

		claw = new Spark(oi.components[0]);

		stick = new Joystick(oi.controllers[0]);
		controller = new XboxController(oi.controllers[1]);

		subs = new Subsystem[] { new Camera(), new Drive(stick, drive), new Claw(controller, claw) };
	}
}
