/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5129.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5129.meta.SAuto;
import org.usfirst.frc.team5129.meta.SSystem;
import org.usfirst.frc.team5129.sys.*;

import java.lang.reflect.Array;

/**
 * Main Robot class.
 *
 * @author kyleg
 */
public class Robot extends TimedRobot {
    private double period;
    private double time;

    private PartMap pmap;

    private Joystick st;
    private XboxController ct;

    private SSystem[] sys;
    private SAuto auto;

    private DashChoice choice;
    private PullAutonomous pull;

    @Override
    public void robotInit() {
        period = 0.05;
        time = 0;
        setPeriod(period);

        pmap = new PartMap();

        st = new Joystick(pmap().port("joy"));
        ct = new XboxController(pmap().port("xbox"));

        sys = new SSystem[]{
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
        choice.addChoice("enable_auto", "Use Auto");
        choice.push();
    }

    @Override
    public void robotPeriodic() {
        time += period;
    }

    @Override
    public void autonomousInit() {
        if (choice.getSelected() == "Use Auto") { // Enable Auto
            auto = pull.findPlate();

            System.out.printf(
                "=== Autonomous ===\n"
                    + "Pos: %d; Instruction: %s\n"
                    + "Decided Auto: %d\n",
                pull.getPlace(), pull.getPlate().toString(), auto);
        } else {
            System.out.println("=== Autonomous has been disabled ===");
        }

        pull = null; // Autonomous pull goes above this.
    }

    @Override
    public void autonomousPeriodic() {
        for (SSystem s : sys) {
            if (!s.getName().equalsIgnoreCase("camera"))
                s.auto(getAuto());
        }
    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {
        for (SSystem s : sys) {
            if (!s.getName().equalsIgnoreCase("camera"))
                s.execute(0x0);
        }
    }

    @Override
    public void disabledInit() {
        for (SSystem s : sys) {
            if (!s.getName().equalsIgnoreCase("camera"))
                s.disable();
        }

        pull = new PullAutonomous(this);
    }

    @Override
    public void disabledPeriodic() {
        pull.pullWhileStarting();
        /*
         * When the plate assignment is pulled, find the
         * autonomous routine to use.
         */
    }

    private SAuto getAuto() {
        return auto;
    }

    public double getTime() {
        return time;
    }

    public PartMap pmap() {
        return pmap;
    }
}

/**
 * A simplified version of the Dashboard Chooser.
 *
 * @author kyleg
 */
class DashChoice {

    private SendableChooser<Object> current;
    private final SendableChooser<Object>[] m_chooser;

    @SuppressWarnings("unchecked")
    DashChoice(SSystem[] systems) {
        current = new SendableChooser<>();
        m_chooser = (SendableChooser<Object>[]) Array.newInstance(current.getClass(), systems.length);
        for (int i = 0; i < m_chooser.length; i++) {
            m_chooser[i] = new SendableChooser<>();
            m_chooser[i].setName(systems[i].getName());
        }
    }

    /**
     * Switches to a sendable.
     *
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
     *
     * @param name   The name of the new choice
     * @param action The possible action, or a display object (can be a String)
     */
    public void addChoice(String name, Object action) {
        current.addObject(name, action);
    }

    /**
     * @return The selected object for the current sendable.
     */
    public Object getSelected() {
        return current.getSelected();
    }

    /**
     * Push changes to the dashboard.
     */
    public void push() {
        SmartDashboard.putData(current);
    }

}

/**
 * Pulls autonomous information while disabled. Set to null when
 * autonomous turns on.
 * <p>
 *
 * @author kyleg
 * @see <a href="https://wpilib.screenstepslive.com/s/currentCS/m/getting_started/l/826278-2018-game-data-details">2018 Game Data</a>
 */
class PullAutonomous {
    private final Robot bot;

    private int place;
    private char[] auto;

    private final DriverStation ds = DriverStation.getInstance();

    PullAutonomous(Robot bot) {
        this.bot = bot;
        this.auto = new char[]{'N', 'N', 'N'};
    }

    /**
     * Called while disabled to pull plate assignments.
     */
    public void pullWhileStarting() {
        String pull = "";
        while (bot.isDisabled() && ds.isFMSAttached()) {
            if (ds.getGameSpecificMessage() != null) {
                place = ds.getLocation();
                pull = ds.getGameSpecificMessage();
                break;
            }
        }
        auto = pull.toCharArray();
    }

    /**
     * Decides the autonomous from the message pulled with {@link PullAutonomous#pullWhileStarting()}
     *
     * @return The suggested routine
     */
    public SAuto findPlate() {
        SAuto auto = SAuto.POS1_LEFT; // Defaults to Pos 1, Left
        char side = getPlate()[0];
        switch (place) { // Decides the autonomous to run based on place.
            // TODO Fix autonomous
            case 1:
                if (side == 'L')
                    auto = SAuto.POS1_LEFT; // Pos 1 Left
                else
                    auto = SAuto.POS1_RIGHT;
                break;
            case 2:
                if (side == 'L')
                    auto = SAuto.POS2_LEFT;
                else
                    auto = SAuto.POS2_RIGHT;
                break;
            case 3:
                if (side == 'L')
                    auto = SAuto.POS3_LEFT;
                else
                    auto = SAuto.POS3_RIGHT;
                break;
        }
        return auto;
    }

    public int getPlace() {
        return place;
    }

    public char[] getPlate() {
        return auto;
    }
}
