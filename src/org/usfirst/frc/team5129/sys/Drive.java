package org.usfirst.frc.team5129.sys;

import org.usfirst.frc.team5129.meta.Handled;
import org.usfirst.frc.team5129.meta.SSystem;
import org.usfirst.frc.team5129.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive extends Handled implements SSystem {
	int time; // It's a field so the VM doesn't recycle it over and over again.
	
	DifferentialDrive drive;
	
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
		time = robot().getTime();
		switch(i) {
		case 0x0:
			double x = getCTRL().getX();
			double y = getCTRL().getY();
			drive.arcadeDrive(x, y);
			break;
		case 0x0a:
			if (time == 0) {
				drive.arcadeDrive(0.5, 0);
			} else if (time == 2) {
				disable();
			}
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
