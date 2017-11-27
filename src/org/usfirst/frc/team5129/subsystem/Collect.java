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

	private boolean isAuto;

	public Collect(GenericHID control, PWMSpeedController controller) {
		super(controller);

		if (control instanceof XboxController) {
			type = DriveType.CONTROLLER;
		} else if (control instanceof Joystick) {
			type = DriveType.JOYSTICK;
		}

		this.isAuto = false;
	}

	public Collect(PWMSpeedController controller) {
		super(controller);

		this.isAuto = false;
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
				case 0:
					decideDrive();
					break;
				case 1:
					getPWM().set(1);
					break;
				case 2:
					getPWM().set(-1);
					break;
				case 3:
					isAuto = true;
					break;
				case 4:
					isAuto = false;
					break;
			}
		}
	}

	@Override
	public boolean onStall() {
		return true;
	}

	@Override
	public void onStop() {
		if (getPWM().isAlive())
			getPWM().disable();
	}

	@Override
	public void onTick() {
		if (isAuto)
			getRoutine().doRoutine();
	}

	private void decideDrive() {
		switch (type) {
			case JOYSTICK:
				getPWM().set(getController().getY());
				break;
			case CONTROLLER:
				int power = 0;
				if (getController().getRawButton(1))
					power = 1;
				else
					power = 0;
				getPWM().set(power);
				break;
			case UNKNOWN:
				DriverStation.reportError(
						"STATE=RUNNING:collect_subsys_type_unknown",
						true);
				break;
		}
	}

	@Override
	public String getName() {
		return "Collect";
	}

	@Override
	public String getDescription() {
		return "Collects and rolls balls from the bot.";
	}

}
