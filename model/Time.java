package ch.zhaw.mas8i.pendlersupport.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Extended Date class that adds some useful time methods
 * and a custom toString method.
 */
public class Time extends Date {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Is this time passed by now?
	 * 
	 * @return Boolean, true means yes, false no
	 */
	public boolean isPassed() {
		return this.getTime() < new Time().getTime();
	}
	
	/**
	 * Reset the time to a given string formatted by the given pattern.
	 * 
	 * @param  string The time string
	 * @param  pattern Defines the formatting of the time string
	 * @return Current time object
	 */
	public Time setFromString(String string, String pattern) throws ParseException {
		this.setTime(new SimpleDateFormat(pattern).parse(string).getTime());
		return this;
	}
	
	/**
	 * Adds the given minutes to the time.
	 * 
	 * @param  minutes Number of minutes
	 * @return Current time object
	 */
	public Time addMinutes(int minutes) {
	    this.setTime(this.getTime() + (minutes * 60000));
	    return this;
	}
	
	/**
	 * Subtracts the given minutes from the time.
	 * 
	 * @param  minutes Number of minutes
	 * @return Current time object
	 */
	public Time subtractMinutes(int minutes) {
		this.setTime(this.getTime() - (minutes * 60000));
		return this;
	}
	
	/**
	 * Compares two time objects.
	 * 
	 * @param  time The time object to compare this with
	 * @return The difference in seconds
	 */
	public int getDiffSeconds(Time time) {
		return (int)((this.getTime() - time.getTime()) / 1000);
	}
	
	/**
	 * Get the time as a string formatted by H:mm.
	 * 
	 * @return The time as a string
	 */
	@Override
	public String toString() {
		return new SimpleDateFormat("H:mm").format(this);
	}
	
	/**
	 * Get the time as a string formatted by an alternative pattern.
	 * 
	 * @param  pattern The pattern to format the time
	 * @return The time as a string
	 */
	public String toString(String pattern) {
		return new SimpleDateFormat(pattern).format(this);
	}
}
