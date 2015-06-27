package ch.zhaw.mas8i.pendlersupport;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

/**
 * Global static logging class. Default log output is
 * System.err. But by default, logging is also disabled.
 */
public class Log {
	private static final Logger LOG = Logger.getAnonymousLogger();
	private static final Level LOG_LEVEL = Level.OFF;
	private static final StreamHandler HANDLER = new StreamHandler(System.err, new SimpleFormatter());
	private static boolean levelSet = false;
	
	/**
	 * The given level is forwarded to the global logger.
	 *
	 * @param logLevel Log level to set
	 */
	public static void setLevel(Level logLevel) {
		LOG.removeHandler(HANDLER);
		LOG.setLevel(logLevel);
		LOG.addHandler(HANDLER);
		
		levelSet = true;
		info("Log level set to " + logLevel.getName());
	}
	
	/**
	 * @param msg Message to forward to the global logger as Level.INFO
	 */
	public static void info(String msg) {
		forwardLog(Level.INFO, msg);
	}
	
	/**
	 * @param msg Message to forward to the global logger as Level.WARNING
	 */
	public static void warning(String msg) {
		forwardLog(Level.WARNING, msg);
	}
	
	/**
	 * @param msg Message to forward to the global logger as Level.SEVERE
	 */
	public static void error(String msg) {
		forwardLog(Level.SEVERE, msg);
	}
	
	private static void forwardLog(Level level, String msg) {
		if (!levelSet) {
			setLevel(LOG_LEVEL);
		}
		
		StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
		LOG.logp(level, caller.getClassName(), caller.getMethodName(), msg);
	}
}
