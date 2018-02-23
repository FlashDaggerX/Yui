package org.usfirst.frc.team5129.meta;

import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team5129.robot.Robot;

/**
 * A component of the bot, controlled by the Xbox Controller, aside
 * the driver.
 *
 * @author kyleg
 */
public abstract class Component extends Piece {
    private final XboxController ct;

    protected Component(Robot bot, XboxController ct) {
        super(bot);
        this.ct = ct;
    }

    protected XboxController getCTRL() {
        return ct;
    }

}
