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
                 /* Each [*] is a foot
                * * * * * * * * * * * * * * * * * * * * *
                * * * * / - - [*] - O - [ ] * * * * * * * Assumed position of the switch
                * * * * | * * * * * * * * * * * * * * * *
                * * * * | * * * * * * * * * * * * * * * *
                * * * * | * * * * * * * * * * * * * * * * Auto Line
                * * * * | * * * * * * * * * * * * * * * *
                * * * * | * * * * * * * * * * * * * * * *
                * * * * | * * * * * * * * * * * * * * * *
                * * * * | * * * * * * * * * * * * * * * *
                * * * * | * * * * * * * * * * * * * * * *
                * * * * | * * * * * * * * * * * * * * * *
                * * * * | * * * * * * * * * * * * * * * *
                * * * * | * * * * * * * * * * * * * * * *
                * * * * | * * * * * | * * * * * | * * * * Alliance wall
                      POS1        POS2        POS3
                 */
                break;
            case POS1_RIGHT:
                routine = POS1_RIGHT();
                 /* Each [*] is a foot
                * * * * * * * * * * * * * * * * * * * * *
                * * * * * * * [ ] - O - [*] - \ * * * * * Assumed position of the switch
                * * * * * * * * * * * * * * * | * * * * *
                * * * * * * * * * * * * * * * | * * * * *
                * * * * * * * * * * * * * * * | * * * * * Auto Line
                * * * * * * * * * * * * * * * | * * * * *
                * * * * * * * * * * * * * * * | * * * * *
                * * * * * * * * * * * * * * * | * * * * *
                * * * * * * * * * * * * * * * | * * * * *
                * * * * * * * * * * * * * * * | * * * * *
                * * * * * * * * * * * * * * * | * * * * *
                * * * * / - - - - - - - - - - / * * * * * (Assumed that POS2 has crossed Auto)
                * * * * | * * * * * * * * * * * * * * * *
                * * * * | * * * * * | * * * * * | * * * * Alliance wall
                      POS1        POS2        POS3
                 */
                break;
            case POS2_LEFT:
                routine = POS2_LEFT();
                 /* Each [*] is a foot
                * * * * * * * * * * * * * * * * * * * * *
                * * * * * * * [*] - O - [ ] * * * * * * * Assumed position of the switch
                * * * * * * * | * * * * * * * * * * * * *
                * * * * * * * \ - - \ * * * * * * * * * *
                * * * * * * * * * * | * * * * * * * * * * Auto Line
                * * * * * * * * * * | * * * * * * * * * *
                * * * * * * * * * * | * * * * * * * * * *
                * * * * * * * * * * | * * * * * * * * * *
                * * * * * * * * * * | * * * * * * * * * *
                * * * * * * * * * * | * * * * * * * * * *
                * * * * * * * * * * | * * * * * * * * * *
                * * * * * * * * * * | * * * * * * * * * *
                * * * * * * * * * * | * * * * * * * * * *
                * * * * | * * * * * | * * * * * | * * * * Alliance wall
                      POS1        POS2        POS3
                 */
                break;
            case POS2_RIGHT: /* Each [*] is a foot
                * * * * * * * * * * * * * * * * * * * * *
                * * * * * * * [ ] - O - [*] * * * * * * * Assumed position of the switch
                * * * * * * * * * * * * * | * * * * * * *
                * * * * * * * * * * / - - / * * * * * * *
                * * * * * * * * * * | * * * * * * * * * * Auto Line
                * * * * * * * * * * | * * * * * * * * * *
                * * * * * * * * * * | * * * * * * * * * *
                * * * * * * * * * * | * * * * * * * * * *
                * * * * * * * * * * | * * * * * * * * * *
                * * * * * * * * * * | * * * * * * * * * *
                * * * * * * * * * * | * * * * * * * * * *
                * * * * * * * * * * | * * * * * * * * * *
                * * * * * * * * * * | * * * * * * * * * *
                * * * * | * * * * * | * * * * * | * * * * Alliance wall
                      POS1        POS2        POS3
                 */

                routine = POS2_RIGHT();
                break;
            case POS3_LEFT:
                 /* Each [*] is a foot
                * * * * * * * * * * * * * * * * * * * * *
                * * * * * / - [*] - O - [ ] * * * * * * * Assumed position of the switch
                * * * * * | * * * * * * * * * * * * * * *
                * * * * * | * * * * * * * * * * * * * * *
                * * * * * | * * * * * * * * * * * * * * * Auto Line
                * * * * * | * * * * * * * * * * * * * * *
                * * * * * | * * * * * * * * * * * * * * *
                * * * * * | * * * * * * * * * * * * * * *
                * * * * * | * * * * * * * * * * * * * * *
                * * * * * | * * * * * * * * * * * * * * *
                * * * * * \ - - - - - - - - - - \ * * * *
                * * * * * * * * * * * * * * * * | * * * *
                * * * * * * * * * * * * * * * * | * * * *
                * * * * | * * * * * | * * * * * | * * * * Alliance wall
                      POS1        POS2        POS3
                 */
                routine = POS3_LEFT();
                break;
            case POS3_RIGHT:
                 /* Each [*] is a foot
                * * * * * * * * * * * * * * * * * * * * *
                * * * * * * * [ ] - O - [ ] - - \ * * * * Assumed position of the switch
                * * * * * * * * * * * * * * * * | * * * *
                * * * * * * * * * * * * * * * * | * * * *
                * * * * * * * * * * * * * * * * | * * * * Auto Line
                * * * * * * * * * * * * * * * * | * * * *
                * * * * * * * * * * * * * * * * | * * * *
                * * * * * * * * * * * * * * * * | * * * *
                * * * * * * * * * * * * * * * * | * * * *
                * * * * * * * * * * * * * * * * | * * * *
                * * * * * * * * * * * * * * * * | * * * *
                * * * * * * * * * * * * * * * * | * * * *
                * * * * * * * * * * * * * * * * | * * * *
                * * * * | * * * * * | * * * * * | * * * * Alliance wall
                      POS1        POS2        POS3
                 */
                routine = POS3_RIGHT();
                break;
            case DEFAULT:
                 /* Each [*] is a foot
                * * * * * * * * * * * * * * * * * * * * *
                * * * * * * * [ ] - O - [ ] * * * * * * * Assumed position of the switch
                * * * * * * * * * * * * * * * * * * * * *
                * * * * ! * * * * * ! * * * * * ! * * * *
                * * * * | * * * * * | * * * * * | * * * * Auto Line
                * * * * | * * * * * | * * * * * | * * * *
                * * * * | * * * * * | * * * * * | * * * *
                * * * * | * * * * * | * * * * * | * * * *
                * * * * | * * * * * | * * * * * | * * * *
                * * * * | * * * * * | * * * * * | * * * *
                * * * * | * * * * * | * * * * * | * * * *
                * * * * | * * * * * | * * * * * | * * * *
                * * * * | * * * * * | * * * * * | * * * *
                * * * * | * * * * * | * * * * * | * * * * Alliance wall
                      POS1        POS2        POS3
                 */
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
                    claw.execute(0x1); // Grip the claw with the cube in it.
                else if (time >= 0.60 && time <= 4.9)
                    drive.execute(0x3); // Drive forward bois
                else if (time >= 5 && time <= 5.2)
                    drive.execute(0x2); // Turn right, duh
                else if (time >= 6 && time <= 6.6)
                    drive.execute(0x3); // Just a tad forward
                else if (time >= 6.6 && time <= 7.2) {
                    drive.disable();
                    arm.execute(0x2); // Lower dat arm
                } else if (time >= 9.6) {
                    arm.disable(); // Stop arm
                    claw.execute(0x2); // Let go of powercubeoid
                    break;
                }
            }
        });
    }

    private Thread POS1_RIGHT() {
        return new Thread(() -> {
            SSystem drive = robot().subsystems()[1],
                    arm = robot().subsystems()[4],
                    claw = robot().subsystems()[2];
            while (time < 15) {
                time = robot().getTime();

                if (time >= 0.20 && time <= 0.60)
                    claw.execute(0x1); // Grip lunch
                else if (time >= 0.80 && time <= 5.5)
                    drive.execute(0x3); // Forward for a bit
                else if (time >= 5.6 && time <= 5.8)
                    drive.execute(0x2); // R I G HT
                else if (time >= 5.9 && time <= 7.5)
                    drive.execute(0x3); // Forward a bit more
                else if (time >= 7.6 && time <= 7.8)
                    drive.execute(0x1); // LE F T
                else if (time >= 7.9 && time <= 10.4)
                    drive.execute(0x3); // Forward even moreeee
                else if (time >= 10.5 && time <= 10.7)
                    drive.execute(0x1); // LE F T AG AIN
                else if (time >= 10.8 && time <= 11.6)
                    drive.execute(0x3); // FORWARD AGAIN!
                else if (time >= 11.7 && time <= 13.2) {
                    drive.disable(); // Stop boi
                    arm.execute(0x2); // LOWER ME
                } else if (time >= 13.3 && time <= 14) {
                    arm.disable(); // STOP WHAT YOU'RE DOING!
                    claw.execute(0x2); // Let go of lunch.
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
            SSystem drive = robot().subsystems()[1];

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
