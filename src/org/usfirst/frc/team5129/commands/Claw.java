package org.usfirst.frc.team5129.commands;

import org.usfirst.frc.team5129.robot.Robot;
import org.usfirst.frc.team5129.robot.interfaces.SIG;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;

public class Claw extends FDCommand {
	
	Spark claw;
	
	XboxController controller;
	
	boolean toggled;
	
	public Claw(Robot bot) {
		super(bot);
		requires(robot().getSBinder().getSubsystem(1));
		
		setName("Claw");
		setSubsystem("sCube");
	}

	@Override
	public void initialize() {
		claw = robot().getHBinder().getClaw();
		
		controller = robot().getHBinder().getController();
		
		toggled = false;
	}

	@Override
	public void execute() {
		boolean eval = robot().isEnabled();
		while (eval) {
			if (getSIG() == SIG.SIG_1) {
				claw.set(1);
			} else if (getSIG() == SIG.SIG_2) {
				claw.set(-1);
			} else if (getSIG() == SIG.STALL) {
				claw.stopMotor();
			}
		}
	}

	@Override
	public void end() {
		claw.stopMotor();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	
	
}
