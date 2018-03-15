package org.usfirst.frc.team5129;

import org.usfirst.frc.team5129.meta.AutoSig;
import org.usfirst.frc.team5129.meta.SubSystem;

public class Auto {
    private volatile Robot bot;
    private volatile double time;

    private Thread routine;

    Auto(Robot bot, AutoSig auto) {
        this.bot = bot;

        switch(auto) {
            case POS1_LEFT:
                routine = POS1_LEFT();

                break;
            case POS1_RIGHT:
                routine = POS1_RIGHT();

                break;
            case POS2_LEFT:
                routine = POS2_LEFT();

                break;
            case POS2_RIGHT:
                routine = POS2_RIGHT();

                break;
            case POS3_LEFT:
                routine = POS3_LEFT();

                break;
            case POS3_RIGHT:
                routine = POS3_RIGHT();

                break;
            case DEFAULT:
                routine = DEFAULT();
                
                break;
        }
    }

    private Thread POS1_LEFT() {
        return new Thread(() -> {
            SubSystem drive = robot().subsystems()[1],
                    arm = robot().subsystems()[4],
                    claw = robot().subsystems()[2];

            while (time < 15) {
                time = robot().getTime();

                if (time >= 0.40 && time <= 4.6)
                    drive.execute(0x3); // Drive forward bois
                else if (time >= 5 && time <= 5.5)
                    drive.execute(0x2); // Turn right, duh
                else if (time >= 5.6 && time <= 6.4) {
                    drive.disable();
                    arm.execute(0x2); // Lower dat arm
                } else if (time >= 9.6)
                    arm.disable(); // Stop arm
                else if (time >= 9.70 && time <= 9.75) {
                    claw.execute(0x2);
                    break;
                }
            }
        });
    }

    private Thread POS1_RIGHT() {
        return new Thread(() -> {
            SubSystem drive = robot().subsystems()[1],
                    arm = robot().subsystems()[4],
                    claw = robot().subsystems()[2];
            while (time < 15) {
                time = robot().getTime();

                if (time >= 0.20 && time <= 1.3)
                    drive.execute(0x3);
                else if (time >= 1.4 && time <= 1.9)
                    drive.execute(0x2);
                else if (time >= 2 && time <= 4.1)
                    drive.execute(0x3);
                else if (time >= 4.2 && time <= 4.7)
                    drive.execute(0x1);
                else if (time >= 4.8 && time <= 5.6)
                    drive.execute(0x3);
                else if (time >= 5.7 && time <= 6.4) {
                    drive.disable();
                    arm.execute(0x2);
                } else if (time >= 6.4 && time <= 6.5) {
                    arm.disable();
                    claw.execute(0x2);
                    break;
                }
            }
        });
    }

    private Thread POS2_LEFT() {
        return new Thread(() -> {
            SubSystem drive = robot().subsystems()[1];

            while (time < 15) {
                time = robot().getTime();

                // Eh, do it later
                // TODO POS2_LEFT Autonomous please?
            }
        });
    }
    private Thread POS2_RIGHT() {
        return new Thread(() -> {

        });
    }

    private Thread POS3_LEFT() {
        return new Thread(() -> {

        });
    }

    private Thread POS3_RIGHT() {
        return new Thread(() -> {

        });
    }

    private Thread DEFAULT() {
        return new Thread(() -> {
            SubSystem drive = robot().subsystems()[1];

            while (time < 15) {
                time = robot().getTime();

                if (time >= 0.60 && time <= 4.9)
                    drive.execute(0x3); // Forward, just to cross auto
                else if (time >= 5) {
                    drive.disable(); // Disable me
                    break;
                }
            }
        });
    }

    private Robot robot() {
        return bot;
    }

    public Thread getRoutine() {
        return routine;
    }
}
