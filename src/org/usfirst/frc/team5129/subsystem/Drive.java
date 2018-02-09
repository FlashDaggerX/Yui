package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.meta.Routine;
import org.usfirst.frc.team5129.meta.Subsystem;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive extends Subsystem {
	
	DifferentialDrive drive;
	
	public Drive(GenericHID controller, DifferentialDrive drive) {
		super(controller);
		
		this.drive = drive;
	}

	@Override
	public void complete(int i) {
		if (getMotorState() == MotorState.RUNNING) {
			switch(i) {
			case 0x0:
				double x = getController().getX();
				double y = getController().getY();
				drive.arcadeDrive(x, y, true);
				break;
			case 0x1a:
				setRoutine(new Routine() {
					
					@Override
					public void doRoutine() {
						if (getTicks() == 2.00) {
							drive.arcadeDrive(0.5, 0);
						} else if (getTicks() == 4.00) {
							stop();
						}
					}
				});
			}
		}
	}
	
	@Override
	public void onStop() {
		drive.stopMotor();
	}

	@Override
	public int getID() {
		return 0xb;
	}

}
