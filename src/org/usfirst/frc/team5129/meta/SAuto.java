package org.usfirst.frc.team5129.meta;

/**
 * Autonomous definitions. It's just easier to read this way.
 *
 * @author kyleg
 */
public enum SAuto {
    POS1_LEFT,
        /*
            < .20 | Forward AND GRIP Claw
            = 5   | Right
            = 6   | Forward
            = 8   | KILL Drive
            = 8.3 | LIFT Arm
            = 9.6 | KILL Arm
            = 9.8 | KILL Claw
         */
    POS1_RIGHT,
        /*
            < .20 | Forward
            = 2   | Right
            = 2.5 | Forward
            = 8   | Left
            = 8.5 | Forward
            = 11  | Left
            = 11.5| Forward
            = 12  | KILL Drive
         */
    POS2_LEFT,
        /*

         */
    POS2_RIGHT,
        /*

         */
    POS3_LEFT,
        /*

         */
    POS3_RIGHT,
        /*

         */

    @SuppressWarnings("unused")
    DEFAULT // Default autonomous. Just crosses the auto line.

}
