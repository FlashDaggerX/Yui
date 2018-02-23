package org.usfirst.frc.team5129.sys;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team5129.meta.Component;
import org.usfirst.frc.team5129.meta.SAuto;
import org.usfirst.frc.team5129.meta.SSystem;
import org.usfirst.frc.team5129.robot.Robot;

public class Claw extends Component implements SSystem {
    private Spark claw;

    public Claw(Robot bot, XboxController ct) {
        super(bot, ct);
    }

    @Override
    public void init() {
        claw = new Spark(robot().pmap().port("claw"));
    }

    @Override
    public void execute(int i) {
        switch (i) {
            case 0x0:
                if (getCTRL().getXButton()) {
                    claw.set(1);
                } else if (getCTRL().getYButton()) {
                    disable();
                }
                break;
            case 0x1:
                claw.set(1);
                break;
        }
    }

    @Override
    public void auto(SAuto i) {
        double time = robot().getTime();
        switch(i) {
            case POS1_LEFT:
                if (time == 0.10)
                    execute(0x1);
                else if (time == 9.8)
                    disable();
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
        claw.set(-0.5);
        claw.stopMotor();
    }

    @Override
    public String getName() {
        return "claw";
    }

}
