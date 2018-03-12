package org.usfirst.frc.team5129.sys;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team5129.Robot;
import org.usfirst.frc.team5129.meta.Component;
import org.usfirst.frc.team5129.meta.SSystem;

public class Claw extends Component implements SSystem {
    private Spark claw;

    private static boolean isTaken = false; // Swap button for X
    private static boolean m_isTaken = false; // Swap for Y

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
                    if (!isTaken) {
                        isTaken = true;
                        new Thread(() -> {
                            long time = System.currentTimeMillis();
                            claw.set(0.8);
                            while (isTaken) {
                                if (System.currentTimeMillis() - time >= 110) {
                                    disable();
                                    isTaken = false;
                                }
                            }
                        }).start();
                    }
                } else if (getCTRL().getYButton()) {
                    if (!m_isTaken) {
                        m_isTaken = true;
                        new Thread(() -> {
                            long time = System.currentTimeMillis();
                            claw.set(-0.8);
                            while (m_isTaken) {
                                if (System.currentTimeMillis() - time >= 115) {
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

    @Override
    public synchronized void disable() {
        claw.stopMotor();
    }

    @Override
    public String getName() {
        return "claw";
    }

}
