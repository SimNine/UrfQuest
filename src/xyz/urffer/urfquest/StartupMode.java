package xyz.urffer.urfquest;

import java.util.HashMap;

/**
 * An enum specifying modes of startup. Either SERVER_ONLY, CLIENT_ONLY,
 * 	or FULL.
 * 
 * @author URF-PC-2020
 *
 */
public enum StartupMode {
	FULL(0),
	CLIENT_ONLY(1),
	SERVER_ONLY(2);
	
	int value;
	private static HashMap<Integer, StartupMode> map = new HashMap<>();
	
	StartupMode(int value) {
		this.value = value;
	}

    static {
        for (StartupMode startupMode : StartupMode.values()) {
            map.put(startupMode.value, startupMode);
        }
    }

    public static StartupMode valueOf(int value) {
        return map.get(value);
    }
}
