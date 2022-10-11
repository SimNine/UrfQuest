package xyz.urffer.urfquest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Logger class. Records logged strings to file, as well as prints them.
 * 
 * @author URF-PC-2020
 * @version %I%, %G%
 *
 * @see LogLevel
 */
public class Logger {
	
	private LogLevel logLevel = LogLevel.NONE;
	
	private String prefix;
	
	private PrintWriter writer;
	
	
	/*
	 * set up logger
	 */
	
	/**
	 * Default logger. Writes any message that's sent with a logging level 
	 * less than than <b>logLevel</b> both to file and to stream. Prefixes all 
	 * messages with <b>prefix</b>.
	 * 
	 * @param logLevel
	 * 	The level of messages to include. This level and every message of 
	 * 	lower level will be logged.
	 * @param prefix
	 * 	The prefix to prepend to all logged messages.
	 * @see LogLevel
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
        
        this.info("Logger initialized with level: " + this.logLevel.name());
	}
	
	public LogLevel getLogLevel() {
		return logLevel;
	}
	
	public void setLogLevel(LogLevel level) {
		this.logLevel = level;
	}
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	
	/*
	 * logging methods
	 */
	
	/**
	 * Logs a message with a log level of ERROR.
	 * 
	 * @param s
	 * 	The message to log.
	 */
	public void error(String s) {
		log("ERROR : " + s, LogLevel.ERROR);
	}

	/**
	 * Logs a message with a log level of WARNING.
	 * 
	 * @param s
	 * 	The message to log.
	 */
	public void warning(String s) {
		log("WARNING : " + s, LogLevel.WARNING);
	}

	/**
	 * Logs a message with a log level of INFO.
	 * 
	 * @param s
	 * 	The message to log.
	 */
	public void info(String s) {
		log("INFO : " + s, LogLevel.INFO);
	}

	/**
	 * Logs a message with a log level of DEBUG.
	 * 
	 * @param s
	 * 	The message to log.
	 */
	public void debug(String s) {
		log("DEBUG : " + s, LogLevel.DEBUG);
	}

	/**
	 * Logs a message with a log level of VERBOSE.
	 * 
	 * @param s
	 * 	The message to log.
	 */
	public void verbose(String s) {
		log("VERBOSE : " + s, LogLevel.VERBOSE);
	}


	/**
	 * Attempts to log the given message with the given log level. If the log 
	 * level given is less than the log level of this Logger, logs it. 
	 * Otherwise, does nothing.
	 * 
	 * @param s
	 * 	The string to log.
	 * @param logLevel
	 * 	The log level with which logging should be attempted.
	 */
	private void log(String s, LogLevel logLevel) {
		if (logLevel.compareTo(this.logLevel) <= 0) {
			s = System.currentTimeMillis() + " " + this.prefix + " " + s;
			
			System.out.println(s);
			writer.println(s);
			writer.flush();
		}
	}

}
