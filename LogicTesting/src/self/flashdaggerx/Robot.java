package self.flashdaggerx;

import self.flashdaggerx.fake.GenericHID;
import self.flashdaggerx.fake.XboxController;
import self.flashdaggerx.meta.SSystem;
import self.flashdaggerx.sys.Drive;

public class Robot extends RobotBase {

    private int time;

    private SSystem[] subs;

    @Override
    public void robotInit() {
        setPeriod(500);

        GenericHID xbox = new XboxController("test");

        subs = new SSystem[] {
                new Drive(xbox, this)
        };
    }

    @Override
    public void robotPeriodic() {
        time += getPeriod();
    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void autonomousPeriodic() {
        for (SSystem s : subs) {
            s.auto(0x0);
        }
    }

    @Override
    public void teleopInit() {
        subs[0].execute(0x0);
    }

    @Override
    public void teleopPeriodic() {
        System.out.println("Running Teleop");
        if (time == 1000)
            kill();
    }

    @Override
    public void disabledInit() {
        /*
        This is the 1st step into the program, after robotInit().
        As soon as the state changes to DISABLED, the robot loop
        is started. Call swapState(DEBUG) to kill the loop, and
        startPeriodic() to start it again.
         */
        swapState(RobotState.AUTONOMOUS);
    }

    @Override
    public void shoutDebug() {
        System.err.println("INTERVAL: " + getPeriod() + "; CURRENT: " + time);
    }

    public int getTime() {
        return time;
    }
}
