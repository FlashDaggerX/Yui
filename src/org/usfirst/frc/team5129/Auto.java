package org.usfirst.frc.team5129;

import org.usfirst.frc.team5129.meta.SAuto;
import org.usfirst.frc.team5129.meta.SSystem;

public class Auto {
    private volatile Robot bot;
    private volatile double time;

    private Thread routine;

    Auto(Robot bot, SAuto auto) {
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
            SSystem drive = robot().subsystems()[1],
                    arm = robot().subsystems()[4],
                    claw = robot().subsystems()[2];

            while (time < 15) {
                time = robot().getTime();

                if (time >= 0.20 && time <= 0.60)
                    claw.execute(0x1);
                else if (time >= 0.60 && time <= 4.9)
                    drive.execute(0x3);
                else if (time >= 5 && time <= 5.2)
                    drive.execute(0x2);
                else if (time >= 6 && time <= 6.4)
                    drive.execute(0x3);
                else if (time >= 8 && time <= 8.1)
                    drive.disable();
                else if (time >= 8.1 && time <= 9.6)
                    arm.execute(0x1);
                else if (time >= 9.6) {
                    arm.disable();
                    claw.disable();
                    break;
                }
            }
        });
    }

    private Thread POS1_RIGHT() {
        return new Thread(() -> {
            SSystem drive = robot().subsystems()[1];

            while (time < 15) {
                time = robot().getTime();

                if (time < 0.20)
                    drive.execute(0x3);
                else if (time == 2)
                    drive.execute(0x2);
                else if (time == 2.5)
                    drive.execute(0x3);
                else if (time == 8)
                    drive.execute(0x1);
                else if (time == 8.5)
                    drive.execute(0x3);
                else if (time == 11)
                    drive.execute(0x1);
                else if (time == 11.5)
                    drive.execute(0x3);
                else if (time == 12) {
                    drive.disable();
                    break;
                }
            }
        });
    }

    private Thread POS2_LEFT() {
        return new Thread(() -> {
            SSystem drive = robot().subsystems()[1];

            while (time < 15) {
                time = robot().getTime();

                if (time < 0.20)
                    drive.execute(0x3);
                else if (time == 2)
                    drive.execute(0x1);
                else if (time == 2.5) {
                    drive.execute(0x3);
                    break;
                }
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
            SSystem drive = robot().subsystems()[1];

            while (time < 15) {
                time = robot().getTime();

                if (time == 0.10)
                    drive.execute(0x3);
                else if (time == 5) {
                    drive.disable();
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
