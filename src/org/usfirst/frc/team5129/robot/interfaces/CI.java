package org.usfirst.frc.team5129.robot.interfaces;

import org.usfirst.frc.team5129.commands.Auto;
import org.usfirst.frc.team5129.commands.FDCommand;
import org.usfirst.frc.team5129.commands.Move;
import org.usfirst.frc.team5129.commands.Stream;
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
	FDCommand[] cCamera;
	
	public CI(Robot bot) {
		this.bot = bot;
		
		cDrive = new FDCommand[] {
				new Move(bot),
				new Auto(bot, Auto.AutoType.T1)
		};
		
		cCamera = new FDCommand[] {
				new Stream(bot)
		};
		
		close();
	}
	
	void close() {
		cDrive[0].cancel();
		cDrive[1].cancel();
		cCamera[0].cancel();
	}
	
	/**
	 * A list of drive commands
	 * <p>
	 * 0 - Move; 1 - Auto;
	 * @param i The command to return
	 * @return A drive command
	 */
	public FDCommand getDriveCommand(int i) {
		return cDrive[i];
	}
	
	/**
	 * A list of camera commands
	 * <p>
	 * 0 - Stream;
	 * @param i The command to return
	 * @return A camera command
	 */
	public FDCommand getCameraCommand(int i) {
		return cCamera[i];
	}
	
	public FDCommand[][] getAllCommands() {
		return new FDCommand[][] { cDrive, cCamera };
	}
	
	// TODO Add more command categorys as they are made
	public int getCommandCount() {
		int a = 0;
		a += cDrive.length;
		a += cCamera.length;
		return a;
	}
}
