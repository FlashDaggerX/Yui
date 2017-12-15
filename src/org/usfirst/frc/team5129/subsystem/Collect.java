package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.safety.MotorState;
import org.usfirst.frc.team5129.subsystem.meta.Component;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.XboxController;

public class Collect extends Component {

	enum DriveType {
		UNKNOWN, JOYSTICK, CONTROLLER;
	}

	private DriveType type = DriveType.UNKNOWN;

	public Collect(GenericHID control, PWMSpeedController controller) {
		super(control, controller);

		if (control instanceof XboxController) {
			type = DriveType.CONTROLLER;
		} else if (control instanceof Joystick) {
			type = DriveType.JOYSTICK;
		}
	}

	public Collect(PWMSpeedController controller) {
		super(controller);
	}

	/*
	 * Functions:
	 * 
	 * [0 - Manual] [1 - Up] [2 - Down] [3 - Swap Auto] [4 - Swap Manual]
	 */
	@Override
	public void complete(byte i) {
		if (getMotorState() == MotorState.RUNNING) {
			switch (i) {
			case 10:
				decideDrive();
				break;
			case 20:
				getPWM().set(1);
				break;
			case 30:
				getPWM().set(-1);
				break;
			}
		}
	}

	private void decideDrive() {
		switch (type) {
		case JOYSTICK:
			getPWM().set(getController().getY());
			break;
		case CONTROLLER:
			int power = 0;
			if (getController().getRawButton(1)) { // A
				power = 1;
			} else if (getController().getRawButton(2)) { // X
				power = -1;
			} else {
				power = 0;
			}
			getPWM().set(power);
			break;
		case UNKNOWN:
			DriverStation.reportError("STATE=RUNNING:collect_subsys_type_unknown",
					true);
			break;
		}
	}

	@Override
	public byte getID() {
		return 40;
	}

}
