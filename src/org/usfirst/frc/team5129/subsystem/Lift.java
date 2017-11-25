package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.safety.MotorState;
import org.usfirst.frc.team5129.subsystem.meta.Subsystem;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.XboxController;

public class Lift extends Subsystem {

	enum DriveType {
		JOYSTICK, CONTROLLER;
	}

	private DriveType type;

	private GenericHID control;

	private PWMSpeedController controller;

	private boolean isAuto;

	public Lift(GenericHID control, PWMSpeedController controller) {
		super();

		if (control instanceof XboxController) {
			type = DriveType.CONTROLLER;
		} else if (control instanceof Joystick) {
			type = DriveType.JOYSTICK;
		}

		this.control = control;
		this.controller = controller;
		this.isAuto = false;
	}

	public Lift(PWMSpeedController controller) {
		super();
		this.controller = controller;
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
					controller.set(1);
					break;
				case 2:
					controller.set(-1);
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
	public void onTick() {
		if (isAuto)
			getRoutine().doRoutine();
	}

	@Override
	public boolean onStall() {
		return true;
	}

	@Override
	public void onStop() {
		if (controller.isAlive())
			controller.disable();
	}

	private void decideDrive() {
		switch (type) {
			case JOYSTICK:
				controller.set(control.getY());
				break;
			case CONTROLLER:
				int power = 0;
				if (control.getRawButton(1))
					power = 1;
				else
					power = 0;
				controller.set(power);
				break;
		// TODO Decide DriveType compatibility
		}
	}

	@Override
	public String getName() {
		return "Lift";
	}

	@Override
	public String getDescription() {
		return "The lift that rolls up the rope, lifting the robot.";
	}

}
