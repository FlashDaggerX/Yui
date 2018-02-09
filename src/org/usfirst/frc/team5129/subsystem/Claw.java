package org.usfirst.frc.team5129.subsystem;

import org.usfirst.frc.team5129.meta.Routine;
import org.usfirst.frc.team5129.meta.Subsystem;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Spark;

public class Claw extends Subsystem {
	
	Spark claw;
	
	public Claw(GenericHID controller, Spark claw) {
		super(controller);
		
		this.claw = claw;
		setDistance(0);
	}

	@Override
	public void complete(int i) {
		if (getMotorState() == MotorState.RUNNING) {
			switch(i) {
			case 0x0:
				if (getDistance() <= 8) {
					if (getController().getRawButton(3)) {
						System.out.println("In");
						claw.set(0.5);
						incDistance();
					} else if (getController().getRawButton(4)) {
						System.out.println("Out");
						claw.set(-0.5);
						decDistance();
					} else if (getController().getRawButton(8)) {
						claw.stopMotor();
						setDistance(0);
					} else {
						claw.stopMotor();
					}
				} else if (getDistance() == 0) {
					claw.stopMotor();
				} else if (getDistance() >= 9) {
					claw.set(-0.5);
					decDistance();
				}
				/*
				 if (getDistance() <= 2) {
					if (getController().getRawButton(3)) {
						System.out.println("In");
						claw.set(1);
						incDistance();
					} else {
						claw.stopMotor();
					}
				} else if (getDistance() != 0 && !(getController().getRawButton(3))) {
					claw.set(-1);
					decDistance();
				} else if (getDistance() == 0){
					claw.stopMotor();
				}
				if (getController().getRawButton(8)) {
					claw.stopMotor();
					setDistance(0);
				}
				System.out.println(getDistance());
				
				if (getDistance() <= 2 && getDistance() >= 1) {
					if (getController().getRawButton(3)) {
						System.out.println("In");
						claw.set(0.5);
						incDistance();
					} else {
						claw.stopMotor();
					}
				} else if (getDistance() != 0) {
					if (getController().getRawButton(4)) {
						System.out.println("Out");
						claw.set(-0.5);
						decDistance();
					} else {
						claw.stopMotor();
					}
				} else if (getDistance() == 0){
					claw.stopMotor();
				}
				if (getController().getRawButton(8)) {
					claw.stopMotor();
					setDistance(0);
				}
				System.out.println(getDistance());
				 */
				break;
			case 0x1:
				claw.set(0.5);
				break;
			case 0x2:
				claw.set(-0.5);
				break;
			case 0x1a:
				setRoutine(new Routine() {

					@Override
					public void doRoutine() {
						if (getTicks() == 0.25) {
							complete(0x1);
						} else if (getTicks() == 0.75) {
							complete(0x2);
						} else if (getTicks() == 1.25) {
							stop();
						}
					}
				});
			}
		}
	}
	
	@Override
	public void onStop() {
		claw.stopMotor();
	}

	@Override
	public int getID() {
		return 0xc;
	}

}
