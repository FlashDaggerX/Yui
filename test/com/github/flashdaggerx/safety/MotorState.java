package com.github.flashdaggerx.safety;

public enum MotorState {

	STOPPED(0),
	STALLED(1),
	RUNNING(2);

	private int id;

	MotorState(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}
}
