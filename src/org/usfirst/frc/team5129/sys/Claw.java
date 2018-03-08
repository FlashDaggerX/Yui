package org.usfirst.frc.team5129.sys;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team5129.Robot;
import org.usfirst.frc.team5129.meta.Component;
import org.usfirst.frc.team5129.meta.SSystem;

public class Claw extends Component implements SSystem {
    private volatile Spark claw;

    private static boolean isTaken = false;
    private static boolean m_isTaken = false;
    //private static boolean swap = false; // Which way can it go?

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
                    if (!isTaken) {
                        isTaken = true;
                        claw.set(0.8);
                    } else {
                        new Thread(() -> {
                            long time = System.currentTimeMillis();
                            while (isTaken) {
                                if (System.currentTimeMillis() - time >= 100) {
                                    disable();
                                    isTaken = false;
                                }
                            }
                        }).start();
                    }
                } else if (getCTRL().getYButton()) {
                    if (!m_isTaken) {
                        m_isTaken = true;
                        claw.set(-0.8);
                    } else {
                        new Thread(() -> {
                            while (m_isTaken) {
                                long time = System.currentTimeMillis();
                                if (System.currentTimeMillis() - time >= 100) {
                                    disable();
                                    m_isTaken = false;
                                }
                            }
                        }).start();
                    }
                } else if (getCTRL().getBackButton()) {
                    disable();
                }
                break;
            case 0x1: // Close
                new Thread(() -> {
                    claw.set(0.8);
                    long time = System.currentTimeMillis();
                    boolean done = false;
                    while (!done) {
                        if (System.currentTimeMillis() - time >= 100) {
                            disable();
                            done = true;
                        }
                    }
                }).start();
                break;
            case 0x2: // Open
                new Thread(() -> {
                    claw.set(-0.8);
                    long time = System.currentTimeMillis();
                    boolean done = false;
                    while (!done) {
                        if (System.currentTimeMillis() - time >= 100) {
                            disable();
                            done = true;
                        }
                    }
                }).start();
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
