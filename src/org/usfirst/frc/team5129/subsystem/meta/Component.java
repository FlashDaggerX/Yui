package org.usfirst.frc.team5129.subsystem.meta;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PWMSpeedController;

/**
 * Same as a subsystem, but defines single-ported components
 * @author kyleg
 *
 */
public abstract class Component extends Subsystem {
	
	private PWMSpeedController control;
	
	public Component(GenericHID controller, PWMSpeedController control) {
		super(controller);
		
		this.control = control;
	}
	
	public Component(PWMSpeedController control) {
		super(null);
		
		this.control = control;
	}

	@Override
	public void onStop() {
		DriverStation.reportWarning("overload_component_onStop()", false);
	}
	
	@Override
	public abstract void complete(byte i);
	
	@Override
	public abstract byte getID();
	
	/**
	 * 
	 * @return The subsystem's PWM controller
	 */
	public PWMSpeedController getPWM() {
		return control;
	}

}
