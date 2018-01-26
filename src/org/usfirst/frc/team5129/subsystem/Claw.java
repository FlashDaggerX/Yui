package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.meta.ControlSafety;
import org.usfirst.frc.team5129.meta.Routine;
import org.usfirst.frc.team5129.meta.Subsystem;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Spark;

public class Claw extends Subsystem {
	
	Spark claw;
	
	double threshold = 0.5;
	
	public Claw(GenericHID controller, Spark claw) {
		super(controller);
		
		this.claw = claw;
	}

	@Override
	public void complete(int i) {
		if (getMotorState() == ControlSafety.MotorState.RUNNING) {
			switch(i) {
			case 0x0:
				// TODO Come up with a controller solution.
				break;
			case 0x1:
				claw.set(threshold);
				break;
			case 0x2:
				claw.set(-threshold);
				break;
			case 0x1a:
				setRoutine(new Routine() {

					@Override
					public void doRoutine() {
						if (getTicks() == 0.25) {
							complete(0x1);
						} else if (getTicks() == 0.75) {
							complete(0x2);
						} else if (getTicks() == 1.25) {
							stop();
						}
					}
				});
			}
		}
	}
	
	@Override
	public void onStop() {
		claw.stopMotor();
	}

	@Override
	public int getID() {
		return 0xc;
	}

}
