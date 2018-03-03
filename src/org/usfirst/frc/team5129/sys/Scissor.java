package org.usfirst.frc.team5129.sys;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team5129.Robot;
import org.usfirst.frc.team5129.meta.Component;
import org.usfirst.frc.team5129.meta.SAuto;
import org.usfirst.frc.team5129.meta.SSystem;

/**
 * Scissor lift, used to lift the end of the {@link Winch} to the bar.
 *
 * @author kyleg
 */
public class Scissor extends Component implements SSystem {
    private Spark scissor;

    public Scissor(Robot bot, XboxController ct) {
        super(bot, ct);
    }

    @Override
    public void init() {
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
    public void auto(SAuto i) {
        switch(i) {
            case POS1_LEFT:
                break;
            case POS1_RIGHT:
                break;
            case POS2_LEFT:
                break;
            case POS2_RIGHT:
                break;
            case POS3_LEFT:
                break;
            case POS3_RIGHT:
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
