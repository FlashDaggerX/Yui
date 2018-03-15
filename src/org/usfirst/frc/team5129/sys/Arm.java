package org.usfirst.frc.team5129.sys;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team5129.Robot;
import org.usfirst.frc.team5129.meta.Component;
import org.usfirst.frc.team5129.meta.SubSystem;

public class Arm extends Component implements SubSystem {
    private Spark arm;

    public Arm(Robot bot, XboxController ct) {
        super(bot, ct);

        arm = new Spark(robot().pmap().port("arm"));
    }

    @Override
    public void execute(int i) {
        switch (i) {
            // Time up is around 2 seconds.
            case 0x0:
                if (getCTRL().getAButton()) {
                    arm.set(-1);
                } else if (getCTRL().getBButton()) {
                    arm.set(0.4);
                } else if (getCTRL().getBackButton()) {
                    disable();
                }
                break;
            case 0x1: // Up
                arm.set(-1);
                break;
            case 0x2: // Down
                arm.set(0.4);
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
