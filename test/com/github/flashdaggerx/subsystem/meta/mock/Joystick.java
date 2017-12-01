package com.github.flashdaggerx.subsystem.meta.mock;

public class Joystick implements GenericHID {

	@Override
	public void getRawAxis(int a) {
		System.out.printf("joystick_getRawAxis(%d)\n", a);
	}

}
