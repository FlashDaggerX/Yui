package org.usfirst.frc.team5129.robot.subsystem.meta;

public enum State {
	
	STALLED(0),
	RUNNING(1),
	STOPPED(2);
	
	private int id;
	
	State(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
}
