package org.usfirst.frc.team5129.sys;

import org.usfirst.frc.team5129.meta.Component;
import org.usfirst.frc.team5129.meta.SSystem;
import org.usfirst.frc.team5129.robot.Robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;

public class Arm extends Component implements SSystem {
	Spark arm;
	
	public Arm(Robot bot, XboxController ct) {
		super(bot, ct);
	}

	@Override
	public void init() {
		arm = new Spark(robot().pmap().port("arm"));
	}

	@Override
	public void execute(int i) {
		switch(i) {
		case 0x0:
			if (getCTRL().getAButton()) {
				arm.set(-1);
			} else if (getCTRL().getBButton()) {
				arm.set(0.5);
			} else if (getCTRL().getBackButton()) {
				disable();
			}
			break;
		}
	}

	@Override
	public void disable() {
		arm.stopMotor();
	}

	@Override
	public String getName() {
		return "arm";
	}

}
