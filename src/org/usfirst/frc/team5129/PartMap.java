package org.usfirst.frc.team5129;

import java.util.HashMap;

/**
 * Ports.
 *
 * @author kyleg
 */
public class PartMap {

    private final HashMap<String, Integer> ports;

    public PartMap() {
        this.ports = new HashMap<>();

        ports.put("drive_fl", 2);
        ports.put("drive_rl", 3);
        ports.put("drive_fr", 1);
        ports.put("drive_rr", 0);

        ports.put("claw", 9);
        ports.put("arm", 8);
        ports.put("winch", 7);
        ports.put("scissor", 6);

        ports.put("joy", 0);
        ports.put("xbox", 1);
    }

    public String keys() {
        return ports.keySet().toString();
    }

    public Integer port(String val) {
        return ports.get(val);
    }

}
