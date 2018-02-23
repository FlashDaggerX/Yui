package org.usfirst.frc.team5129.sys;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team5129.meta.Handled;
import org.usfirst.frc.team5129.meta.SAuto;
import org.usfirst.frc.team5129.meta.SSystem;
import org.usfirst.frc.team5129.robot.Robot;

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
                double x = getCTRL().getX();
                double y = getCTRL().getY();
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
                if (time == 0.10)
                    execute(0x3);
                else if (time == 5)
                    execute(0x2);
                else if (time == 6)
                    execute(0x3);
                else if (time == 8)
                    disable();
                break;
            case POS1_RIGHT:
                break;
            case POS2_LEFT:
                break;
            case POS2_RIGHT:
                break;
            case POS3_LEFT:
                break;
            case POS3_RIGHT:
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

}
