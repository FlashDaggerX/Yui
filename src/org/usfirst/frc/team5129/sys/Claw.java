package org.usfirst.frc.team5129.sys;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team5129.Robot;
import org.usfirst.frc.team5129.meta.Component;
import org.usfirst.frc.team5129.meta.SAuto;
import org.usfirst.frc.team5129.meta.SSystem;

public class Claw extends Component implements SSystem {
    private Spark claw;

    private static boolean isTaken = false;
    private static boolean m_isTaken = false;
    private static boolean swap = false; // Which way can it go?

    private double time;

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
                    if (!swap) { // Is the claw open?
                        if (!isTaken) { // Any threads running?

                            time = System.currentTimeMillis();
                            isTaken = true;
                            claw.set(0.8);
                        } else {
                            // Start ticking thread
                            new Thread(() -> {
                                while (isTaken) {
                                    if (System.currentTimeMillis() - time >= 100) {
                                        disable();
                                        isTaken = false;
                                        
                                        swap = true;
                                    }
                                }
                                System.out.println("exiting thread.");
                            }).start();
                        }
                    }
                } else if (getCTRL().getYButton()) {
                    if (swap) { // Is the claw closed?
                        if (!m_isTaken) { // Any threads running?

                            time = System.currentTimeMillis();
                            m_isTaken = true;
                            claw.set(-0.8);
                        } else {
                            new Thread(() -> {
                                while (m_isTaken) {
                                    if (System.currentTimeMillis() - time >= 100) {
                                        disable();
                                        m_isTaken = false;

                                        swap = false;
                                    }
                                    System.out.println("threading");
                                }
                            }).start();
                        }
                    }
                } else if (getCTRL().getBackButton()) {
                    disable();
                }
                break;
            case 0x1:
                claw.set(1);
                break;
            case 0x2:
                claw.set(-0.3);
                break;
            case 0x3:
                claw.stopMotor();
                break;
        }
    }

    @Override
    public void auto(SAuto i) {
        double time = robot().getTime();
        switch(i) {
            case POS1_LEFT:
                if (time < 0.20)
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
        claw.stopMotor();
    }

    @Override
    public String getName() {
        return "claw";
    }

}
