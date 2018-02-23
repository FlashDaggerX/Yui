package org.usfirst.frc.team5129.meta;

import org.usfirst.frc.team5129.robot.Robot;

/**
 * Base for drive-team controlled parts.
 *
 * @author kyleg
 */
public abstract class Piece {

    private final Robot bot;

    Piece(Robot bot) {
        this.bot = bot;
    }

    protected Robot robot() {
        return bot;
    }

}
