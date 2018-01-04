package org.usfirst.frc.team5129.robot.interfaces;

import org.usfirst.frc.team5129.commands.Move;
import org.usfirst.frc.team5129.commands.FDCommand;
import org.usfirst.frc.team5129.robot.Robot;

/**
 * Contains command instances. Used for bundling commands together in one group. The
 * commands that run forever should be in their own group.
 * 
 * @author kyleg
 */
public class CI {
	
	Robot bot;
	
	FDCommand[] cDrive;
	
	public CI(Robot bot) {
		this.bot = bot;
		
		cDrive = new FDCommand[] {
			new Move(bot)
		};
	}
	
	public FDCommand[] getDriveCommands() {
		return cDrive;
	}
}
