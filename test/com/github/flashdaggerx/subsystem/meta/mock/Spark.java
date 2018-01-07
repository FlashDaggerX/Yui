package com.github.flashdaggerx.subsystem.meta.mock;

public class Spark implements PWMSpeedController {

	@Override
	public void set(double speed) {
		System.out.printf("spark_set(%d)", speed);
	}

	@Override
	public void stop() {
		System.out.println("spark_stopped");
	}

}
