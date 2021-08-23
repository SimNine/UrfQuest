package urfquest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Logger {
	
	public static int LOG_ALL = -1;
	public static int LOG_NONE = 0;
	public static int LOG_ERROR = 1;
	public static int LOG_WARNING = 2;
	public static int LOG_INFO = 3;
	public static int LOG_DEBUG = 4;
	public static int LOG_VERBOSE = 5;
	
	private int logLevel = LOG_NONE;
	
	private PrintWriter writer;
	
	public Logger(int logLevel) {
		this.logLevel = logLevel;
		
		File logDir = new File("logs");
        if (!logDir.exists()) {
        	logDir.mkdir();
        }
        
        File log = new File(logDir, System.currentTimeMillis() + ".log");
        try {
			writer = new PrintWriter(log);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
        
        this.all("Logger initialized");
	}
	
	public int getLogLevel() {
		return logLevel;
	}
	
	
	
	public void all(String s) {
		log("ALL: " + s, LOG_ALL);
	}
	
	public void error(String s) {
		log("ERROR: " + s, LOG_ERROR);
	}
	
	public void warning(String s) {
		log("WARNING: " + s, LOG_WARNING);
	}
	
	public void info(String s) {
		log("INFO: " + s, LOG_INFO);
	}
	
	public void debug(String s) {
		log("DEBUG: " + s, LOG_DEBUG);
	}
	
	public void verbose(String s) {
		log("VERBOSE: " + s, LOG_VERBOSE);
	}
	
	
	
	private void log(String s, int logLevel) {
		if (logLevel <= this.logLevel) {
			s = System.currentTimeMillis() + ": " + s;
			
			System.out.println(s);
			writer.println(s);
			writer.flush();
		}
	}

}
