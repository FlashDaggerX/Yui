package org.usfirst.frc.team5129;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5129.meta.SAuto;
import org.usfirst.frc.team5129.meta.SSystem;
import org.usfirst.frc.team5129.sys.*;

import java.util.Arrays;

import static org.usfirst.frc.team5129.meta.SAuto.*;

/**
 * Main Robot class.
 *
 * @author kyleg
 */
public class Robot extends TimedRobot {
    private double period; // Time in between periodic() calls
    private double time; // Total time

    private PartMap pmap; // The Part Map for the robot

    private SSystem[] sys; // All the subsystems, stored in an array
    private SAuto auto; // The Autonomous state.
    private boolean loopAuto; // Loop the autonomous?

    private DashChoice choice;
    private PullAutonomous pull;

    @Override
    public void robotInit() {
        period = 0.05;
        time = 0;
        setPeriod(period);

        pmap = new PartMap();
        System.out.printf("PartMap Keys: %s", pmap().keys());

        Joystick st = new Joystick(pmap().port("joy"));
        XboxController ct = new XboxController(pmap().port("xbox"));

        sys = new SSystem[]{
            new Camera(),
            new Drive(this, st),
            new Claw(this, ct),
            new Scissor(this, ct),
            new Arm(this, ct),
            new Winch(this, ct)
        };

        auto = null;
        loopAuto = false;

        for (SSystem s : sys) {
            s.init();
        }

        choice = new DashChoice();
        choice.addChoice("Use Auto", "enable_auto", false);
        choice.addChoice("Default Auto", "default_auto", false);
        choice.addChoice("Disable Auto", "disable_auto", true);
        choice.push();
    }

    @Override
    public void robotPeriodic() {
        time += period;
    }

    @Override
    public void autonomousInit() {
        switch (choice.getSelected()) {
            case "enable_auto":  // Enable Auto
                auto = pull.findPlate();

                System.out.printf(
                        "=== Autonomous ===\n"
                                + "Pos: %d; Instruction: %s\n"
                                + "Decided Auto: %s\n",
                        pull.getPlace(), Arrays.toString(pull.getPlate()), auto.toString());

                loopAuto = true;
                break;
            case "default_auto":
                auto = DEFAULT;
                System.out.println("=== Autonomous has been set to default ===");

                loopAuto = true;
                break;
            case "disable_auto":
                auto = null;
                System.out.println("=== Autonomous has been disabled ===");

                loopAuto = false;
                break;
        }

        pull = null; // Autonomous pull goes above this.
    }

    @Override
    public void autonomousPeriodic() {
        if (loopAuto) {
            for (SSystem s : sys) {
                if (!s.getName().equalsIgnoreCase("camera"))
                    s.auto(getAuto());
            }
        }
    }

    @Override
    public void teleopInit() {
        sys[5].execute(0x3);
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

        auto = null;
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

    private SendableChooser<String> m_chooser;

    DashChoice() {
        m_chooser = new SendableChooser<>();
    }

    /**
     * Add a choice to the current sendable.
     *
     * @param name   The name of the new choice
     * @param action The possible action, or a display object (can be a String)
     * @param def Is it a default choice?
     */
    public void addChoice(String name, String action, boolean def) {
        if (!def)
            m_chooser.addObject(name, action);
        else
            m_chooser.addDefault(name, action);
    }

    /**
     * @return The selected object for the current sendable.
     */
    public String getSelected() {
        return m_chooser.getSelected();
    }

    /**
     * Push changes to the dashboard.
     */
    public void push() {
        SmartDashboard.putData("Sendable", m_chooser);
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
        // TODO Add FMS check before competition (ds.isFMSAttached())
        while (bot.isDisabled()) {
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
        SAuto auto = POS1_LEFT; // Defaults to Pos 1, Left
        char side = getPlate()[0];
        switch (place) { // Decides the autonomous to run based on place.
            case 1:
                if (side == 'L')
                    auto = POS1_LEFT; // Pos 1 Left
                else
                    auto = POS1_RIGHT;
                break;
            case 2:
                if (side == 'L')
                    auto = POS2_LEFT;
                else
                    auto = POS2_RIGHT;
                break;
            case 3:
                if (side == 'L')
                    auto = POS3_LEFT;
                else
                    auto = POS3_RIGHT;
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
