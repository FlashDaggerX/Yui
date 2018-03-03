package org.usfirst.frc.team5129.sys;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team5129.Robot;
import org.usfirst.frc.team5129.meta.Component;
import org.usfirst.frc.team5129.meta.SAuto;
import org.usfirst.frc.team5129.meta.SSystem;

public class Arm extends Component implements SSystem {
    private Spark arm;

    public Arm(Robot bot, XboxController ct) {
        super(bot, ct);
    }

    @Override
    public void init() {
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
                    arm.set(0.6);
                } else if (getCTRL().getBackButton()) {
                    disable();
                }
                break;
            case 0x1: // Up
                arm.set(-1);
                break;
            case 0x2: // Down
                arm.set(0.6);
                break;
        }
    }

    @Override
    public void auto(SAuto i) {
        double time = robot().getTime();
        switch(i) {
            case POS1_LEFT:
                if (time == 8.3)
                    execute(0x1);
                else if (time == 9.6)
                    disable();
                break;
            case POS1_RIGHT:
                if (time == 0) // Fix please
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
        arm.stopMotor();
    }

    @Override
    public String getName() {
        return "arm";
    }

}
