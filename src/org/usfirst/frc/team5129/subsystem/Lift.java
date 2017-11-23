package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.subsystem.meta.Routine;
import org.usfirst.frc.team5129.subsystem.meta.State;
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
	
	public Lift(GenericHID control, PWMSpeedController controller) {
		super();
		if (control instanceof XboxController) {
			type = DriveType.CONTROLLER;
		} else if (control instanceof Joystick) {
			type = DriveType.JOYSTICK;
		}
		this.control = control;
		this.controller = controller;
	}
	
	public Lift(PWMSpeedController controller) {
		super();
		this.controller = controller;
	}
	
	@Override
	public void complete(int i, Routine r) {
		if (getMotorState() == State.RUNNING) {
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
			}
		}
	}

	@Override
	public void onTick() {
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
		switch(type) {
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
	public void onKill() {
		stop();
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
