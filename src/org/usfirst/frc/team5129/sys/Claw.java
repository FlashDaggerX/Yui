package org.usfirst.frc.team5129.sys;

import org.usfirst.frc.team5129.meta.Component;
import org.usfirst.frc.team5129.meta.SSystem;
import org.usfirst.frc.team5129.robot.Robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;

public class Claw extends Component implements SSystem {
	Spark claw;
	
	public Claw(Robot bot, XboxController ct) {
		super(bot, ct);
	}

	@Override
	public void init() {
		claw = new Spark(robot().pmap().port("claw"));
	}

	@Override
	public void execute(int i) {
		switch(i) {
		case 0x0:
			if (getCTRL().getXButton()) {
				claw.set(1);
			} else if (getCTRL().getYButton()) {
				disable();
			}
			break;
		}
	}

	@Override
	public void disable() {
		claw.set(-0.5);
		claw.stopMotor();
	}

	@Override
	public String getName() {
		return "claw";
	}

}
