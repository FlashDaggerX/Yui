/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5129.robot;

import java.lang.reflect.Array;

import org.usfirst.frc.team5129.meta.Routine;
import org.usfirst.frc.team5129.meta.SSystem;
import org.usfirst.frc.team5129.sys.Arm;
import org.usfirst.frc.team5129.sys.Camera;
import org.usfirst.frc.team5129.sys.Claw;
import org.usfirst.frc.team5129.sys.Drive;
import org.usfirst.frc.team5129.sys.Scissor;
import org.usfirst.frc.team5129.sys.Winch;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	int time;
	
	PartMap pmap;
	
	Joystick st;
	XboxController ct;
	
	SSystem[] sys;
	int auto;
	
	DashChoice choice;
	
	@Override
	public void robotInit() {
		setPeriod(0.05);
		time = 0;
		
		pmap = new PartMap();
		
		st = new Joystick(pmap().port("joy"));
		ct = new XboxController(pmap().port("xbox"));
		
		sys = new SSystem[] {
			new Camera(),
			new Drive(this, st),
			new Claw(this, ct),
			new Scissor(this, ct),
			new Arm(this, ct),
			new Winch(this, ct)
		};
		
		for (SSystem s : sys) {
			s.init();
		}
		
		choice = new DashChoice(sys);
		choice.reset("drive");
		choice.addChoice("auto_one", "Default Auto");
		choice.finalize();
	}
	
	@Override
	public void robotPeriodic() {
		time++;
	}
	
	@Override
	public void autonomousInit() {
		if (choice.getSelected().equals("Default Auto")) {
			auto = 0x0a;
		}
	}
	
	@Override
	public void autonomousPeriodic() {
		for (SSystem s : sys) {
			if (!s.getName().equalsIgnoreCase("camera"))
				s.execute(auto);
		}
	}
	
	@Override
	public void teleopPeriodic() {
		for (SSystem s : sys) {
			if (!s.getName().equalsIgnoreCase("camera"))
				s.execute(0x0);
		}
	}
	
	public int getTime() {
		return time;
	}
	
	public PartMap pmap() {
		return pmap;
	}
}

class DashChoice {
	
	SendableChooser<Object> current;
	SendableChooser<Object>[] m_chooser;
	
	@SuppressWarnings("unchecked")
	DashChoice(SSystem[] systems) {
		current = new SendableChooser<>();
		m_chooser = (SendableChooser<Object>[]) Array.newInstance(current.getClass(), systems.length);
		for (int i = 0; i < m_chooser.length; i++) {
			m_chooser[i] = new SendableChooser<Object>();
			m_chooser[i].setName(systems[i].getName());
		}
	}
	
	/**
	 * Switches to a sendable.
	 * @param name Name of the sendable
	 */
	public void reset(String name) {
		for (SendableChooser<Object> g : m_chooser) {
			if (g.getName().equalsIgnoreCase(name)) {
				current = g;
				break;
			}
		}
	}
	
	/**
	 * Add a choice to the current sendable.
	 * @param name The name of the new choice
	 * @param action The possible action, or a display object (can be a String)
	 */
	public void addChoice(String name, Object action) {
		current.addObject(name, action);
	}
	
	/**
	 * Adds a routine to the current sendable.
	 * @param name The name of the new routine
	 * @param routine The routine to be called
	 */
	public void addRoutine(String name, Routine routine) {
		current.addObject(name, routine);
	}
	
	/**
	 * 
	 * @return The selected object for the current sendable.
	 */
	public Object getSelected() {
		return current.getSelected();
	}
	
	/**
	 * Push changes to the dashboard.
	 */
	public void finalize() {
		SmartDashboard.putData(current);
	}
	
}
