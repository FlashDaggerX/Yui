package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.meta.Subsystem;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Spark;

public class Arm extends Subsystem {
	
	Spark arm;
	
	public Arm(GenericHID controller, Spark arm) {
		super(controller);
		
		this.arm = arm;
	}

	@Override
	public void complete(int i) {
		if (getMotorState() == MotorState.RUNNING) {
			switch(i) {
			case 0x0:
				if (getController().getRawButton(1)) {
					arm.set(-1);
				} else if (getController().getRawButton(2)){
					arm.set(1);
				} else if (getController().getRawButton(7)) {
					arm.stopMotor();
				}
				break;
			case 0x1:
				arm.set(-1);
				break;
			case 0x2:
				arm.set(1);
				break;
			}
		}
	}
	
	@Override
	public void onStop() {
		arm.stopMotor();
	}

	@Override
	public int getID() {
		return 0xd;
	}

}
