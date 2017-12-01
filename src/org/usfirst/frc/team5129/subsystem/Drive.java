package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.safety.MotorState;
import org.usfirst.frc.team5129.subsystem.meta.Subsystem;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drive extends Subsystem {
	
	private RobotDrive drive;
	
	public Drive(GenericHID controller, RobotDrive drive) {
		super(controller);
		
		this.drive = drive;
	}
	
	@Override
	public void complete(byte i) {
		if (getMotorState() == MotorState.RUNNING) {
			switch (i) {
				case 10:
					drive.arcadeDrive(getController());
					break;
				case 20:
					drive.drive(1, 0);
					// TODO Actually do something here :/
			}
		}
	}

	@Override
	public byte getID() {
		return 10;
	}

}
