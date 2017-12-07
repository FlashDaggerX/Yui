package org.usfirst.frc.team5129.safety;

public enum MotorState {

	STOPPED(0),
	RUNNING(1);

	private int id;

	MotorState(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}
}
