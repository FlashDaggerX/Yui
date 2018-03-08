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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

    private DashChoice choice;
    private PullAutonomous pull;

    @Override
    public void robotInit() {
        period = 0.10;
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

        for (SSystem s : sys) {
            s.init();
        }

        choice = new DashChoice("Auto");
        choice.addChoices(new String[] {"disable_auto", "default_auto", "enable_auto"});
        choice.push();
    }

    @Override
    public void robotPeriodic() {
        time += period;
    }

    @Override
    public void autonomousInit() {
        boolean loopAuto = false; // Start the autonomous?
        switch (choice.getSelected()) {
            case "enable_auto":
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

        time = 0;

        if (loopAuto) {
            Auto a = new Auto(this, auto);
            a.getRoutine().start(); // Begin autonomous.
        }
    }

    @Override
    public void teleopInit() {
        subsystems()[5].execute(0x3);
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

    public double getTime() {
        return time;
    }

    public SSystem[] subsystems() {
        return sys;
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

    /*
    The reason behind using a HashMap instead of simplifying it was
    because it made more sense for me, since a Sendable wasn't an option.
     */
    private HashMap<String, String[]> choices;
    private String current; // The current section of the list;

    DashChoice(String firstKey) {
        choices = new HashMap<>();
        current = firstKey;
    }

    /**
     * Adds a choice to the selected key.
     * @param names The names of the choices. The first one is the default.
     */
    public void addChoices(String[] names) {
        if (!choices.containsKey(current)) {
            choices.put(current, names);
        } else {
            DriverStation.reportError(
                    "USR: Can't add choice key.",
                    false);
        }
    }

    /**
     * Changes the key.
     * @param key The name of the key. If you have already pushed
     *            this key to the dash, you can only read it.
     */
    public void swap(String key) {
        this.current = key;
    }

    /**
     * Pushes the current key to the dash.
     */
    public void push() {
        SmartDashboard.putStringArray(current, choices.get(current));
    }

    /**
     * Gets the selected object from the key.
     * @return The object selected, or the first if it could not find one.
     */
    public String getSelected() {
        return SmartDashboard.getString(current, choices.get(current)[0]);
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
        SAuto auto = DEFAULT;
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
