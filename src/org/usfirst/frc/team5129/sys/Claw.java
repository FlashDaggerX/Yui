package org.usfirst.frc.team5129.sys;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team5129.Robot;
import org.usfirst.frc.team5129.meta.Component;
import org.usfirst.frc.team5129.meta.SubSystem;

public class Claw extends Component implements SubSystem {
    private Spark[] claw;
    
    public Claw(Robot bot, XboxController ct) {
        super(bot, ct);

        claw = new Spark[] {
        		new Spark(robot().pmap().port("claw_1")),
        		new Spark(robot().pmap().port("claw_2"))
        };
    }

    @Override
    public void execute(int i) {
        switch (i) {
            case 0x0:
            	if (getCTRL().getXButton())
            		inward();
            	else if (getCTRL().getYButton())
            		outward();
                break;
            case 0x1: // Close
                new Thread(() -> {
                    inward();
                	long time = System.currentTimeMillis();
                    
                    while(true) {
                    	if ((System.currentTimeMillis() - time) == 120) {
                    		disable();
                    		break;
                    	}
                    }
                }).start();
                break;
            case 0x2: // Open
                new Thread(() -> {
                	 outward();
                 	long time = System.currentTimeMillis();
                     
                     while(true) {
                     	if ((System.currentTimeMillis() - time) == 120) {
                     		disable();
                     		break;
                     	}
                     }
                }).start();
                break;
            case 0x3:
                disable();
                break;
        }
    }

    @Override
    public synchronized void disable() {
        for (Spark s : claw) {
        	s.stopMotor();
        }
    }

    @Override
    public String getName() {
        return "claw";
    }
    
    private void inward() {
    	claw[0].set(-0.8);
    	claw[1].set(0.8);
    }
    
    private void outward() {
    	claw[0].set(0.8);
    	claw[1].set(-0.8);
    }
}
