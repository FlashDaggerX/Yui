package self.flashdaggerx;

public class Main {

    /*
    You should always build the project from here.
     */

    public static void main(String[] args) {
        RobotBase bot;
        bot = new Robot();

        bot.robotInit();
        System.out.println("robotInit() has returned!");

        bot.start();
        bot.init();

        bot.swapState(RobotBase.RobotState.DISABLED);
        bot.startPeriodic();
    }
}
