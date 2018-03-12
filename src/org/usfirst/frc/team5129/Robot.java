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

    private String choice;
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

        for (SSystem s : sys) {
            s.init();
        }

        SmartDashboard.putString("auto_", "enable_auto");
    }

    @Override
    public void robotPeriodic() {
        time += period;
    }

    @Override
    public void autonomousInit() {
        boolean loopAuto = false; // Start the autonomous?
        switch (choice) {
            case "enable_auto":
                auto = pull.findPlate(0);

                System.out.printf(
                        "=== Autonomous ===\n"
                                + "Pos: %d; Instruction: %s\n"
                                + "Decided Auto: %s\n",
                        pull.getPlace(), Arrays.toString(pull.getPlate()), auto.toString());

                loopAuto = true;
                break;
            case "pos1_pull":
                auto = pull.findPlate(1);

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
            default:
                System.out.println("Could not pull Autonomous.");
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

        choice = SmartDashboard.getString("auto_", "disable_auto");
        //choice = SmartDashboard.getString("Auto", "disable_auto");
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
                System.out.println("Pulling Auto....");
                pull = ds.getGameSpecificMessage();
                break;
            }
        }
        auto = pull.toCharArray();
    }

    /**
     * Decides the autonomous from the message pulled with {@link PullAutonomous#pullWhileStarting()}
     *
     * @param p Optional place, 0 for pull all
     * @return The suggested routine
     */
    public SAuto findPlate(int p) {
        SAuto auto = DEFAULT;
        char side = getPlate()[0];
        if (p == 0) {
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
        } else if (p == 1) {
            if (side == 'L')
                auto = POS1_LEFT; // Pos 1 Left
            else
                auto = POS1_RIGHT;
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
