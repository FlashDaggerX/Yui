package self.flashdaggerx.sys;

import self.flashdaggerx.Robot;
import self.flashdaggerx.fake.GenericHID;
import self.flashdaggerx.meta.Handled;
import self.flashdaggerx.meta.SSystem;

public class Drive extends Handled implements SSystem {
    private Robot bot;

    public Drive(GenericHID controller, Robot bot) {
        super(controller);

        this.bot = bot;
    }

    @Override
    public void init() {

    }

    @Override
    public void execute(int i) {
        switch(i) {
            case 0x0:
                System.out.println("Case 0x0");
                getController().drive(0.99, 0.56);
                break;
            default:
                break;
        } // here; break
    }

    @Override
    public void auto(int i) {
        int time = bot.getTime();
        switch(i) {
            case 0x0:
                if (time == 5000)
                    execute(0x0);
                break;
        }
    }

    @Override
    public void disable() {

    }
}
