package org.usfirst.frc.team5129.sys;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team5129.Robot;
import org.usfirst.frc.team5129.meta.Component;
import org.usfirst.frc.team5129.meta.SubSystem;

/**
 * Scissor lift, used to lift the end of the {@link Winch} to the bar.
 *
 * @author kyleg
 */
public class Scissor extends Component implements SubSystem {
    private Spark scissor;

    public Scissor(Robot bot, XboxController ct) {
        super(bot, ct);

        scissor = new Spark(robot().pmap().port("scissor"));
    }

    @Override
    public void execute(int i) {
        switch (i) {
            case 0x0:
                // Time up is 2.96 seconds
                if (getCTRL().getBumper(Hand.kLeft)) {
                    scissor.set(1);
                } else if (getCTRL().getBumper(Hand.kRight)) {
                    scissor.set(-0.6);
                } else if (getCTRL().getBackButton()) {
                    disable();
                }
                break;
            case 0x1: // Up
                scissor.set(1);
                break;
            case 0x2: // Down
                scissor.set(-0.6);
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
