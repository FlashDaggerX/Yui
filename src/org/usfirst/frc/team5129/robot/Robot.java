package org.usfirst.frc.team5129.robot;

import org.usfirst.frc.team5129.robot.interfaces.CI;
import org.usfirst.frc.team5129.robot.interfaces.HI;
import org.usfirst.frc.team5129.robot.interfaces.OI;
import org.usfirst.frc.team5129.robot.interfaces.SI;
import org.usfirst.frc.team5129.robot.interfaces.SIG;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	SendableChooser<Command> m_chooser;
	
	OI buttonBinder;
	HI hardwareBinder;
	SI subsystemBinder;
	CI commandBinder;
	
	double period;
	double selfTime;
	
	@Override
	public void robotInit() {
		period = 0.02;
		setPeriod(period);
		
		m_chooser = new SendableChooser<>();
		
		// FIXME Be aware of null objects when initiating the interfaces. Order matters.
		new RobotMap();
		buttonBinder = new OI();
		hardwareBinder = new HI();
		subsystemBinder = new SI(this);
		commandBinder = new CI(this);
		
		for (Subsystem s : getSBinder().getAllSubsystems()) {
			s.getDefaultCommand().start();
		}
		
		SmartDashboard.putData("Auto mode", m_chooser);
	}
	
	@Override
	public void robotPeriodic() {
		setTimer(getTime() + period);
	}
	
	@Override
	public void disabledInit() {
		for (int i = 0; i < getCBinder().getAllCommands().length; i++) {
			for (int y = 0; y < getCBinder().getAllCommands()[i].length; y++) {
				getCBinder().getAllCommands()[i][y].cancel();
			}
		}
		resetTimer();
	}
	
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void autonomousInit() {
		getCBinder().getDriveCommand(0).cancel();
		resetTimer();
		getCBinder().getDriveCommand(1).start();
		getCBinder().getDriveCommand(1).sendSIG(SIG.SIG_1);
	}
	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void teleopInit() {
		getCBinder().getDriveCommand(1).cancel();
		resetTimer();
		getCBinder().getDriveCommand(0).start();
	}
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
	
	/* Robot Binders */
	public OI getBBinder() {
		return buttonBinder;
	}
	
	public HI getHBinder() {
		return hardwareBinder;
	}
	
	public SI getSBinder() {
		return subsystemBinder;
	}
	
	public CI getCBinder() {
		return commandBinder;
	}
	
	/* Timers */
	public void setTimer(double t) {
		selfTime = t;
	}
	
	public void resetTimer() {
		selfTime = 0;
	}
	
	public double getTime() {
		return selfTime;
	}
	
}
