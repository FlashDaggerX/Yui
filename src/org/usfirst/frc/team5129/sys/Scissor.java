package org.usfirst.frc.team5129.sys;

import org.usfirst.frc.team5129.meta.Component;
import org.usfirst.frc.team5129.meta.SSystem;
import org.usfirst.frc.team5129.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;

/**
 * Scissor lift, used to lift the end of the {@link Winch} to the bar.
 * @author kyleg
 *
 */
public class Scissor extends Component implements SSystem {
	Spark scissor;
	
	public Scissor(Robot bot, XboxController ct) {
		super(bot, ct);
	}

	@Override
	public void init() {
		scissor = new Spark(robot().pmap().port("scissor"));
	}

	@Override
	public void execute(int i) {
		switch(i) {
		case 0x0:
			if (getCTRL().getBumper(Hand.kLeft)) {
				scissor.set(1);
			} else if (getCTRL().getBumper(Hand.kRight)) {
				scissor.set(-1);
			} else if (getCTRL().getBackButton()) {
				disable();
			}
			break;
		}
	}

	@Override
	public void disable() {
		scissor.stopMotor();
	}

	@Override
	public String getName() {
		return "scissor";
	}

}
