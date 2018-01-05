package org.usfirst.frc.team5129.robot;

import org.usfirst.frc.team5129.robot.interfaces.CI;
import org.usfirst.frc.team5129.robot.interfaces.HI;
import org.usfirst.frc.team5129.robot.interfaces.OI;
import org.usfirst.frc.team5129.robot.interfaces.SI;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	SendableChooser<Command> m_chooser;
	
	OI buttonBinder;
	HI hardwareBinder;
	SI subsystemBinder;
	CI commandBinder;
	
	@Override
	public void robotInit() {
		setPeriod(1.0);
		
		m_chooser = new SendableChooser<>();
		
		// FIXME Be aware of null objects when initiating the interfaces. Order matters.
		new RobotMap();
		buttonBinder = new OI();
		hardwareBinder = new HI(HI.DriveType.DIFFERENTIAL);
		subsystemBinder = new SI(this);
		commandBinder = new CI(this);
		
		getSubsystemBinder().getSubsystems()[1].getDefaultCommand().start();
		
		SmartDashboard.putData("Auto mode", m_chooser);
	}
	
	@Override
	public void disabledInit() {
		for (Command c : getCommandBinder().getDriveCommands()) {
			c.cancel();
		}
	}
	
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void autonomousInit() {
		
	}
	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void teleopInit() {
		
	}
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
	
	/* Robot Binders */
	public OI getButtonBinder() {
		return buttonBinder;
	}
	
	public HI getHardwareBinder() {
		return hardwareBinder;
	}
	
	public SI getSubsystemBinder() {
		return subsystemBinder;
	}
	
	public CI getCommandBinder() {
		return commandBinder;
	}
	
}
