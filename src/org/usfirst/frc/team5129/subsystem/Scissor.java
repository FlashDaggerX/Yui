package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.meta.Subsystem;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Spark;

public class Scissor extends Subsystem {
	
	Spark scissor;
	
	public Scissor(GenericHID controller, Spark scissor) {
		super(controller);
		
		this.scissor = scissor;
		setDistance(0);
	}

	@Override
	public void complete(int i) {
		if (getMotorState() == MotorState.RUNNING) {
			switch(i) {
			case 0x0:
				// Time up is 2.9 secs (2.96 precision)
//				if (getDistance() <= 8) {
//					if (getController().getRawButton(5)) {
//						
//					}
//				}
				if (getController().getRawButton(5)) {
					scissor.set(1); // up
				} else if (getController().getRawButton(6)) {
					scissor.set(-1);
				} else if (getController().getRawButton(7)) {
					scissor.stopMotor();
				}
				break;
			case 0x1:
				break;
			}
		}
	}

	@Override
	public int getID() {
		return 0xd;
	}

}
