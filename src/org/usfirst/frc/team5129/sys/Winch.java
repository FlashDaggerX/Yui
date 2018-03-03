package org.usfirst.frc.team5129.sys;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team5129.Robot;
import org.usfirst.frc.team5129.meta.Component;
import org.usfirst.frc.team5129.meta.SAuto;
import org.usfirst.frc.team5129.meta.SSystem;

public class Winch extends Component implements SSystem {
    private Spark winch;

    public Winch(Robot bot, XboxController ct) {
        super(bot, ct);
    }

    @Override
    public void init() {
        winch = new Spark(robot().pmap().port("winch"));
    }

    @Override
    public void execute(int i) {
        switch (i) {
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
            case 0x1:
                winch.set(1);
                break;
            case 0x2:
                winch.set(-1);
                break;
            case 0x3: // Winch Debug
                if (getCTRL().getRawAxis(2) > 0.2) {
                    double x = getCTRL().getTriggerAxis(Hand.kLeft);
                    winch.set(x);
                } else {
                    disable();
                }
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
        winch.stopMotor();
    }

    @Override
    public String getName() {
        return "winch";
    }

}
