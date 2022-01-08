package urfquest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Logger {
	
	public enum LogLevel {
		LOG_ALL,
		LOG_NONE,
		LOG_ERROR,
		LOG_WARNING,
		LOG_INFO,
		LOG_DEBUG,
		LOG_VERBOSE
	}
	
	private LogLevel logLevel = LogLevel.LOG_NONE;
	
	private String prefix;
	
	private PrintWriter writer;
	
	public Logger(LogLevel logLevel, String prefix) {
		this.logLevel = logLevel;
		this.prefix = prefix;
		
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
        
        this.all("Logger initialized with level: " + this.logLevel.name());
	}
	
	public LogLevel getLogLevel() {
		return logLevel;
	}
	
	public void setLogLevel(LogLevel level) {
		this.logLevel = level;
	}
	
	
	
	public void all(String s) {
		log("ALL: " + s, LogLevel.LOG_ALL);
	}
	
	public void error(String s) {
		log("ERROR: " + s, LogLevel.LOG_ERROR);
	}
	
	public void warning(String s) {
		log("WARNING: " + s, LogLevel.LOG_WARNING);
	}
	
	public void info(String s) {
		log("INFO: " + s, LogLevel.LOG_INFO);
	}
	
	public void debug(String s) {
		log("DEBUG: " + s, LogLevel.LOG_DEBUG);
	}
	
	public void verbose(String s) {
		log("VERBOSE: " + s, LogLevel.LOG_VERBOSE);
	}
	
	
	
	private void log(String s, LogLevel logLevel) {
		if (logLevel.compareTo(this.logLevel) <= 0) {
			s = System.currentTimeMillis() + " - " + this.prefix + ": " + s;
			
			System.out.println(s);
			writer.println(s);
			writer.flush();
		}
	}

}
