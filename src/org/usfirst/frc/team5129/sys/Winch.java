package org.usfirst.frc.team5129.sys;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team5129.Robot;
import org.usfirst.frc.team5129.meta.Component;
import org.usfirst.frc.team5129.meta.SubSystem;

public class Winch extends Component implements SubSystem {
    private Spark winch;

    private boolean isDebug;

    public Winch(Robot bot, XboxController ct) {
        super(bot, ct);

        isDebug = false;
        winch = new Spark(robot().pmap().port("winch"));
    }

    @Override
    public void execute(int i) {
        switch (i) {
            case 0x0:
                if (isDebug) {
                    if (getCTRL().getPOV() == 180 || getCTRL().getPOV() == 225) {
                        if (getCTRL().getRawAxis(2) > 0.2) {
                            double x = getCTRL().getTriggerAxis(Hand.kLeft);
                            winch.set(-x);
                        } else {
                            disable();
                        }
                    } else if (getCTRL().getPOV() == -1) {
                        if (getCTRL().getRawAxis(2) > 0.2) {
                            double x = getCTRL().getTriggerAxis(Hand.kLeft);
                            winch.set(x);
                        } else {
                            disable();
                        }
                    }
                } else {
                    if (getCTRL().getRawAxis(2) > 0.2) {
                        double x = getCTRL().getTriggerAxis(Hand.kLeft);
                        winch.set(x);
                    } else {
                        disable();
                    }
                }
                break;
            case 0x1:
                winch.set(1);
                break;
            case 0x2:
                winch.set(-1);
                break;
            case 0x3: // Winch Debug
                isDebug = true;
                break;
            case 0x4:
                isDebug = false;
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
