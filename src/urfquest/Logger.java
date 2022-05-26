package urfquest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Logger {
	
	private LogLevel logLevel = LogLevel.NONE;
	
	private String prefix;
	
	private PrintWriter writer;
	
	
	/*
	 * set up logger
	 */
	
	public Logger(LogLevel logLevel, String prefix) {
		this.logLevel = logLevel;
		this.prefix = prefix;
		
		File logDir = new File("logs");
        if (!logDir.exists()) {
        	logDir.mkdir();
        }
        
        File log = new File(logDir, System.currentTimeMillis() + "_" + prefix + ".log");
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
	
	
	/*
	 * logging methods
	 */
	
	public void all(String s) {
		log("ALL : " + s, LogLevel.ALL);
	}
	
	public void error(String s) {
		log("ERROR : " + s, LogLevel.ERROR);
	}
	
	public void warning(String s) {
		log("WARNING : " + s, LogLevel.WARNING);
	}
	
	public void info(String s) {
		log("INFO : " + s, LogLevel.INFO);
	}
	
	public void debug(String s) {
		log("DEBUG : " + s, LogLevel.DEBUG);
	}
	
	public void verbose(String s) {
		log("VERBOSE : " + s, LogLevel.VERBOSE);
	}
	
	
	
	private void log(String s, LogLevel logLevel) {
		if (logLevel.compareTo(this.logLevel) <= 0) {
			s = System.currentTimeMillis() + " " + this.prefix + " " + s;
			
			System.out.println(s);
			writer.println(s);
			writer.flush();
		}
	}

}
