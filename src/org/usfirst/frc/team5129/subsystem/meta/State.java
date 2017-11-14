package org.usfirst.frc.team5129.subsystem.meta;

public enum State {
	
	STOPPED(0),
	STALLED(1),
	RUNNING(2);
	
	private int id;
	
	State(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
}
