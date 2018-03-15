package org.usfirst.frc.team5129.meta;

/**
 * Subsystem base.
 *
 * @author kyleg
 */
public interface SubSystem {

    /**
     * Endlessly called in periodic() methods.
     *
     * @param i The routine
     */
    void execute(int i);

    /**
     * Called when the subsystem is disabled.
     */
    void disable();

    /**
     * @return The name of the subsystem
     */
    String getName();
}
