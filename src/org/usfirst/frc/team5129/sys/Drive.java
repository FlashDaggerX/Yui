package org.usfirst.frc.team5129.sys;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team5129.Robot;
import org.usfirst.frc.team5129.meta.Handled;
import org.usfirst.frc.team5129.meta.SAuto;
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
                double deadzone;
                if (getCTRL().getTrigger())
                    deadzone = 0.4;
                else
                    deadzone = 0.2;
                double x = createDeadZone(getCTRL().getX(), deadzone);
                double y = createDeadZone(getCTRL().getY(), deadzone);
                drive.arcadeDrive(x, y, true);
                break;
            case 0x1: // Left
                drive.arcadeDrive(0, -1);
                break;
            case 0x2: // Right
                drive.arcadeDrive(0, 1);
                break;
            case 0x3: // Forward
                drive.arcadeDrive(0.6, 0);
                break;
            case 0x4: // Back
                drive.arcadeDrive(-0.6, 0);
                break;
        }
    }

    @Override
    public void auto(SAuto i) {
        double time = robot().getTime();
        switch(i) {
            case POS1_LEFT:
                if (time < 0.20)
                    execute(0x3);

                else if (time == 5)
                    execute(0x2);

                else if (time == 6)
                    execute(0x3);

                else if (time == 8)
                    disable();
                break;
            case POS1_RIGHT:
                if (time < 0.20)
                    execute(0x3);

                else if (time == 2)
                    execute(0x2);

                else if (time == 2.5)
                    execute(0x3);

                else if (time == 8)
                    execute(0x1);

                else if (time == 8.5)
                    execute(0x3);

                else if (time == 11)
                    execute(0x1);

                else if (time == 11.5)
                    execute(0x3);

                else if (time == 12)
                    disable();
                break;
            case POS2_LEFT:
                if (time < 0.20)
                    execute(0x3);

                else if (time == 2)
                    execute(0x1);

                else if (time == 2.5)
                    execute(0x3);
                break;
            case POS2_RIGHT:
                break;
            case POS3_LEFT:
                break;
            case POS3_RIGHT:
                break;
            case DEFAULT:
                if (time == 0.10)
                    execute(0x3);
                else if (time == 5)
                    disable();
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
     * @param input
     * @param deadZoneSize
     * @return adjusted_input
     *
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
