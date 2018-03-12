package org.usfirst.frc.team5129.sys;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team5129.Robot;
import org.usfirst.frc.team5129.meta.Handled;
import org.usfirst.frc.team5129.meta.SSystem;

public class Drive extends Handled implements SSystem {
    private DifferentialDrive drive;

    public Drive(Robot bot, Joystick st) {
        super(bot, st);
    }

    @Override
    public void init() {
        final PWMVictorSPX[] pwm = {
                new PWMVictorSPX(robot().pmap().port("drive_fl")),
                new PWMVictorSPX(robot().pmap().port("drive_rl")),
                new PWMVictorSPX(robot().pmap().port("drive_fr")),
                new PWMVictorSPX(robot().pmap().port("drive_rr")),
        };

        final SpeedControllerGroup[] grp = {
                new SpeedControllerGroup(pwm[0], pwm[1]),
                new SpeedControllerGroup(pwm[2], pwm[3])
        };

        grp[1].setInverted(true);

        drive = new DifferentialDrive(grp[0], grp[1]);

    }

    @Override
    public void execute(int i) {
        switch (i) {
            case 0x0: // Default
                /*
                double deadzone;
                if (getCTRL().getTrigger())
                    deadzone = 0.5;
                else
                    deadzone = 0.3;
                */
                double x = getCTRL().getX() * 0.8;
                double y = getCTRL().getY() * 0.8;
                drive.arcadeDrive(x, y, true);
                break;
            /*
                Forward is [+] on right motor, [-] on left motor
                Back is [-][+]
                Left is [-][-]
                Right is[+][+]
            */
            case 0x1: // Left
                drive.tankDrive(-0.5, -0.5);
                break;
            case 0x2: // Right
                drive.tankDrive(0.5, 0.5);
                break;
            case 0x3: // Forward
                drive.tankDrive(-0.6, 0.6);
                break;
            case 0x4: // Back
                drive.tankDrive(0.6, -0.6);
                break;
        }
    }

    @Override
    public void disable() {
        drive.stopMotor();
    }

    @Override
    public String getName() {
        return "drive";
    }

    /**
     * Creates a deadzone, but without clipping the lower values.
     * turns this
     * |--1--2--3--4--5--|
     * into this
     * ______|-1-2-3-4-5-|
     *
     * @param input The controller input.
     * @param deadZoneSize The size of the deadzone.
     * @return adjusted_input
     * @author owatonnarobotics
     * @see <a href=https://github.com/owatonnarobotics/XboxController/blob/master/XboxController.java>Custom Xbox Class</a>
     */
    private double createDeadZone(double input, double deadZoneSize) {
        final double negative;
        double deadZoneSizeClamp = deadZoneSize;
        double adjusted;

        if (deadZoneSizeClamp < 0 || deadZoneSizeClamp >= 1) {
            deadZoneSizeClamp = 0;  // Prevent any weird errors
        }

        negative = input < 0 ? -1 : 1;

        adjusted = Math.abs(input) - deadZoneSizeClamp;  // Subtract the deadzone from the magnitude
        adjusted = adjusted < 0 ? 0 : adjusted;          // if the new input is negative, make it zero
        adjusted = adjusted / (1 - deadZoneSizeClamp);   // Adjust the adjustment so it can max at 1

        return negative * adjusted;
    }

}
