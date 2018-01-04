package com.github.flashdaggerx.subsystem.meta;

import com.github.flashdaggerx.subsystem.meta.mock.GenericHID;
import com.github.flashdaggerx.subsystem.meta.mock.PWMSpeedController;

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
	public abstract boolean onStall();

	@Override
	public abstract void onStop();
	
	@Override
	public abstract void complete(int i);
	
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
