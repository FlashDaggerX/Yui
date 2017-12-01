package com.github.flashdaggerx.subsystem;

import com.github.flashdaggerx.safety.MotorState;
import com.github.flashdaggerx.subsystem.meta.Subsystem;
import com.github.flashdaggerx.subsystem.meta.mock.GenericHID;

public class Drive extends Subsystem {

	public Drive(GenericHID controller) {
		super(controller);
	}

	@Override
	public void complete(int i) {
		if (getMotorState() == MotorState.RUNNING) {
			switch (i) {
				case 0:
					System.out.println("drive_subsys_complete(1)");
					break;
			}
		}
	}

	@Override
	public byte getID() {
		return 100;
	}

}
