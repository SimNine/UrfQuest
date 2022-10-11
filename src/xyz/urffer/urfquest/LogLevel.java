package xyz.urffer.urfquest;

/**
 * Used by Logger to send messages of the log level specified by this enum.
 * 
 * @author URF-PC-2020
 * @version %I%, %G%
 * 
 * @see Logger
 *
 */
public enum LogLevel {
	NONE,
	
	/**
	 * Error logging. Most selective logging level.
	 * No other logs are displayed.
	 */
	ERROR,
	
	/**
	 * Warning logging. Log whenever something potentially problematic but not 
	 * breaking happens. 
	 * <p>
	 * Also displays logs of level:
	 * <ul>
	 * <li> ERROR
	 * </ul>
	 */
	WARNING,
	
	/**
	 * Default level of logging. Log whenever something notable happens. 
	 * <p>
	 * Also displays logs of level:
	 * <ul>
	 * <li> ERROR
	 * <li> WARNING
	 * </ul>
	 */
	INFO,
	
	/**
	 * Extra level of logging. Used for more fine-grained logging than INFO. 
	 * <p>
	 * Also displays logs of level:
	 * <ul>
	 * <li> ERROR
	 * <li> WARNING
	 * <li> INFO
	 * </ul>
	 */
	DEBUG,
	
	/**
	 * Verbose logging. Used for extremely fine-grained logging. Do not use 
	 * this level too heavily, or it will clutter logs. 
	 * <p>
	 * Also displays logs of level:
	 * <ul>
	 * <li> ERROR
	 * <li> WARNING
	 * <li> INFO
	 * <li> DEBUG
	 * </ul>
	 */
	VERBOSE,
	
	/**
	 * Displays all logging: 
	 * <ul>
	 * <li> ERROR
	 * <li> WARNING
	 * <li> INFO
	 * <li> DEBUG
	 * <li> VERBOSE
	 * </ul>
	 */
	ALL
}
