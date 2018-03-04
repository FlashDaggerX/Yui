package self.flashdaggerx;

import java.util.Timer;
import java.util.TimerTask;

public abstract class RobotBase {
    protected enum RobotState {
        AUTONOMOUS, TELEOP, TEST, DISABLED, DEBUG
    }

    private RobotState state = RobotState.DEBUG;

    private int period;

    private Timer time;
    private boolean start;
    private boolean init;

    public RobotBase() {
        time = new Timer();
        period = 1000;
        start = false;
        init = false;
    }

    public abstract void robotInit();
    public void robotPeriodic() {}
    public abstract void autonomousInit();
    public abstract void autonomousPeriodic();
    public abstract void teleopInit();
    public abstract void teleopPeriodic();
    public void testInit() {}
    public void testPeriodic() {}
    public abstract void disabledInit();
    public void disabledPeriodic() {}

    public abstract void shoutDebug();

    protected void swapState(RobotState state) {
        if (init) {
            if (state != getState()) {
                this.state = state;
                System.out.println("STATE: " + getState().toString());

                switch (getState()) {
                    case AUTONOMOUS:
                        autonomousInit();
                        break;
                    case TELEOP:
                        teleopInit();
                        break;
                    case TEST:
                        testInit();
                        break;
                    case DISABLED:
                        disabledInit();
                        break;
                    case DEBUG:
                        time.cancel();
                        System.err.println("Main loop stopped. init() methods can still be called.");
                        start = true;
                        break;
                }
            } else {
                System.err.println("ERR: STATE is already " + getState().toString());
            }
        } else {
            System.err.println("ERR: robotInit() has not returned yet!");
        }
    }

    private void periodic() {
        robotPeriodic();
        shoutDebug();
        switch(getState()) {
            case AUTONOMOUS:
                autonomousPeriodic();
                break;
            case TELEOP:
                teleopPeriodic();
                break;
            case TEST:
                testPeriodic();
                break;
            case DISABLED:
                disabledPeriodic();
                break;
        }
    }

    public void startPeriodic() {
        if (start) {
            time.schedule(new TimerTask() {

                @Override
                public void run() {
                    periodic();
                }
            }, 0, getPeriod());
            System.out.println("Main loop initialized");
            start = false;
        } else {
            System.err.println("ERR: A second loop cannot be started next to the main one.");
        }
    }

    protected void kill() {
        System.err.println("Killing Simulation...");
        System.exit(0);
    }

    public void init() {
        this.init = true;
    }

    public void start() {
        this.start = true;
        System.out.println("A loop can now be started.");
    }

    public void setPeriod(int millis) {
        this.period = millis;
    }

    public int getPeriod() {
        return period;
    }

    public RobotState getState() {
        return state;
    }

}
