package com.github.flashdaggerx.robot;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
	
	enum State {
		INIT, DISABLED, AUTO, TELEOP, PRACTICE, TEST;
	}
	
	private State s = State.INIT;
	
	private Timer t;
	
	private static Main robot;
	
	public static void main(String[] args) {
		robot = new Robot();
		
		if (robot instanceof Main) {
			((Main) robot).setup();
		} else {
			robot.setup();
		}
	}
	
	public void setup() {
		t = new Timer();
		
		robotInit();
		
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				switch(s) {
					case AUTO:
						autonomousPeriodic();
						break;
					case DISABLED:
						disabledPeriodic();
						break;
					case PRACTICE:
						practicePeriodic();
						break;
					case TELEOP:
						teleopPeriodic();
						break;
					case TEST:
						testPeriodic();
						break;
					default:
						System.out.println("er_unknown_state_terminating");
						kill();
						break;
				}
			}
			
		}, 0, 1000);
		
		s = State.DISABLED;
		disabledInit();
	}
	
	public synchronized void swap(int state) {
		if (s != State.INIT) {
			switch(state) {
				case 0:
					s = State.DISABLED;
					disabledInit();
					break;
				case 1:
					s = State.AUTO;
					autonomousInit();
					break;
				case 2:
					s = State.TELEOP;
					teleopInit();
					break;
				case 3:
					s = State.PRACTICE;
					practiceInit();
					break;
				case 4:
					s = State.TEST;
					testInit();
					break;
			}
		}
	}
	
	public synchronized void robotInit() {
		
	}
	
	public synchronized void autonomousInit() {
		
	}
	
	public synchronized void autonomousPeriodic() {
		kill();
	}
	
	public synchronized void teleopInit() {
		
	}
	
	public synchronized void teleopPeriodic() {
		kill();
	}
	
	public synchronized void practiceInit() {
		
	}
	
	public synchronized void practicePeriodic() {
		kill();
	}
	
	public synchronized void testInit() {
		
	}
	
	public synchronized void testPeriodic() {
		kill();
	}
	
	public synchronized void disabledInit() {
		
	}
	
	public synchronized void disabledPeriodic() {
		kill();
	}
	
	public synchronized void kill() {
		if (!(s == State.INIT)) {
			t.cancel();
			System.out.println("robot_simulation_killed");
		} else {
			System.out.println("er_robot_cant_kill_while_init");
		}
	}
}
