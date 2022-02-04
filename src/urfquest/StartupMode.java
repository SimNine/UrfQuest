package urfquest;

import java.util.HashMap;

public enum StartupMode {
	FULL(0),
	CLIENT_ONLY(1),
	SERVER_ONLY(2);
	
	private int value;
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
        return (StartupMode) map.get(value);
    }
}
