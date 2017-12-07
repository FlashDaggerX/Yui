package org.usfirst.frc.team5129.robot;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team5129.subsystem.Camera;
import org.usfirst.frc.team5129.subsystem.Collect;
import org.usfirst.frc.team5129.subsystem.Drive;
import org.usfirst.frc.team5129.subsystem.Lift;
import org.usfirst.frc.team5129.subsystem.meta.Routine;
import org.usfirst.frc.team5129.subsystem.meta.Subsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends IterativeRobot {

	private RobotDrive drive;

	private Spark lift;
	private Spark collect;

	private Joystick stick;
	private XboxController controller;

	private Subsystem[] subs;

	@Override
	public void robotInit() {

		OI oi = new OI();

		drive = new RobotDrive(oi.motors[0], oi.motors[1], oi.motors[2],
				oi.motors[3]);

		lift = new Spark(oi.components[0]);
		collect = new Spark(oi.components[1]);

		stick = new Joystick(oi.controllers[0]);
		controller = new XboxController(oi.controllers[1]);

		subs = new Subsystem[] { new Drive(stick, drive), new Camera(),
				new Lift(controller, lift), new Collect(controller, collect) };

		subs[1].start();
		subs[1].complete((byte) 10);

		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				for (Subsystem s : subs) {
					s.tick();
				}
			}
		}, 0, 1000);
	}

	@Override
	public void autonomousInit() {
		subs[0].start();
		subs[0].resetTicks();
		subs[0].setRoutine(new Routine() {
			@Override
			public void doRoutine() {
				if (subs[0].getTicks() == 3) {
					subs[0].complete((byte) 20);
				}
				if (subs[0].getTicks() == 5) {
					subs[0].stop();
				}
			}

		});
	}

	@Override
	public void autonomousPeriodic() {
		while (isEnabled()) {
			subs[0].getRoutine().doRoutine();
		}
	}

	@Override
	public void teleopInit() {
		for (Subsystem s : subs) {
			s.start();
		}
		subs[0].complete((byte) 50);
	}

	@Override
	public void teleopPeriodic() {
		while (isOperatorControl() && isEnabled()) {
			for (Subsystem s : subs) {
				if (s.getID() != (byte) 20)
					s.complete((byte) 10);
			}
		}
	}

	@Override
	public void disabledInit() {
		for (Subsystem s : subs) {
			s.resetTicks();
			if (s.getID() != (byte) 20)
				s.stop();
		}
	}
}
