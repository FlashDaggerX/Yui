package org.usfirst.frc.team5129.meta;

import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team5129.robot.Robot;

/**
 * Components handled by the driver.
 *
 * @author kyleg
 */
public abstract class Handled extends Piece {
    private final Joystick st;

    protected Handled(Robot bot, Joystick st) {
        super(bot);
        this.st = st;
    }

    protected Joystick getCTRL() {
        return st;
    }

}
