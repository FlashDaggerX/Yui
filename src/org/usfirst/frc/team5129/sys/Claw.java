package org.usfirst.frc.team5129.sys;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team5129.Robot;
import org.usfirst.frc.team5129.meta.Component;
import org.usfirst.frc.team5129.meta.SSystem;

public class Claw extends Component implements SSystem {
    private Spark claw;

    private static boolean isTaken = false;
    private static boolean m_isTaken = false;
    //private static boolean swap = false; // Which way can it go?

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
                if (claw.getSpeed() != 0) {
                    disable();
                }
                if (getCTRL().getXButton()) {
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

                                    //swap = true;
                                }
                            }
                        }).start();
                    }
                } else if (getCTRL().getYButton()) {
                    if (!m_isTaken) { // Any threads running?

                        time = System.currentTimeMillis();
                        m_isTaken = true;
                        claw.set(-0.8);
                    } else {
                        new Thread(() -> {
                            // Start ticking thread
                            while (m_isTaken) {
                                if (System.currentTimeMillis() - time >= 100) {
                                    disable();
                                    m_isTaken = false;

                                    //swap = false;
                                }
                            }
                        }).start();
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

    /*
    if (claw.getSpeed() != 0) {
                    disable();
                }
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
                                // Start ticking thread
                                while (m_isTaken) {
                                    if (System.currentTimeMillis() - time >= 100) {
                                        disable();
                                        m_isTaken = false;

                                        swap = false;
                                    }
                                }
                            }).start();
                        }
                    }
                } else if (getCTRL().getBackButton()) {
                    disable();
                }
     */

    @Override
    public void disable() {
        claw.stopMotor();
    }

    @Override
    public String getName() {
        return "claw";
    }

}
