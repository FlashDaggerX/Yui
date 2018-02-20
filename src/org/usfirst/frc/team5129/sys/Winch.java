package org.usfirst.frc.team5129.sys;

import org.usfirst.frc.team5129.meta.Component;
import org.usfirst.frc.team5129.meta.SSystem;
import org.usfirst.frc.team5129.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;

public class Winch extends Component implements SSystem {
	Spark winch;
	
	public Winch(Robot bot, XboxController ct) {
		super(bot, ct);
	}

	@Override
	public void init() {
		winch = new Spark(robot().pmap().port("winch"));
	}

	@Override
	public void execute(int i) {
		switch(i) {
		case 0x0:
			if (getCTRL().getPOV() == 180 || getCTRL().getPOV() == 225) {
				if (getCTRL().getRawAxis(2) > 0.2) {
					double x = getCTRL().getTriggerAxis(Hand.kLeft);
					winch.set(x);
				} else {
					disable();
				}
			} else if (getCTRL().getPOV() == -1) {
				if (getCTRL().getRawAxis(2) > 0.2) {
					double x = getCTRL().getTriggerAxis(Hand.kLeft);
					winch.set(-x);
				} else {
					disable();
				}
			}
			break;
		}
	}

	@Override
	public void disable() {
		winch.stopMotor();
	}

	@Override
	public String getName() {
		return "winch";
	}

}
