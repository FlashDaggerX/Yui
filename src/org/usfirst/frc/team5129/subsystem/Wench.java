package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.meta.Subsystem;

import edu.wpi.first.wpilibj.GenericHID;

public class Wench extends Subsystem {

	public Wench(GenericHID controller) {
		super(controller);
	}

	@Override
	public void complete(int i) {
		if (getMotorState() == MotorState.RUNNING) {
			System.out.println("STATE=TELE:empty_");
		}
	}

	@Override
	public int getID() {
		return 0xe;
	}

}
