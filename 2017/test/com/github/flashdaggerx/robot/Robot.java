package com.github.flashdaggerx.robot;

import com.github.flashdaggerx.subsystem.Drive;
import com.github.flashdaggerx.subsystem.meta.Subsystem;
import com.github.flashdaggerx.subsystem.meta.mock.Joystick;

public class Robot extends Main {
	
	private int time;
	
	private Joystick stick;
	
	private Subsystem[] subs;
	
	@Override
	public void robotInit() {
		stick = new Joystick();
		
		subs = new Subsystem[] {
			new Drive(stick)	
		};
		subs[0].start();
	}
	
	@Override
	public void teleopPeriodic() {
		time++;
		subs[0].complete(0);
		
		if (time == 5) {
			kill();
		}
	}
	
	@Override
	public void disabledInit() {
		swap(2);
	}
	
}
