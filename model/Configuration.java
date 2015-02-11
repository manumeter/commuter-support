package ch.zhaw.mas8i.pendlersupport.model;

import java.io.Serializable;

/**
 * Configuration for the whole application, containing
 * a connection, the offset to walk, the offset to sprint
 * and the max. wait time.
 */
public class Configuration implements Serializable {
	
	private Connection connection;
	private int offsetWalk;
	private int offsetSprint;
	private int maxWait;
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor needs all values contained in the
	 * configuration.
	 * 
	 * @param connection Connection to set
	 * @param offsetWalk Offset if the user walks (in minutes)
	 * @param offsetSprint Offset if the user sprints (in minutes)
	 * @param maxWait The maximum the user wants to wait at the station (in minutes)
	 */
	public Configuration(Connection connection, int offsetWalk, int offsetSprint, int maxWait) {
		this.connection = connection;
		this.offsetWalk = offsetWalk;
		this.offsetSprint = offsetSprint;
		this.maxWait = maxWait;
	}
	
	/**
	 * @return Current connection
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * @return Current offset, if the user walks (in minutes)
	 */
	public int getOffsetWalk() {
		return offsetWalk;
	}
	
	/**
	 * @return Current offset, if the user sprints (in minutes)
	 */
	public int getOffsetSprint() {
		return offsetSprint;
	}
	
	/**
	 * @return Current time, the user wants to wait at most at the station (in minutes)
	 */
	public int getMaxWait() {
		return maxWait;
	}
	
	/**
	 * @param connection Connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * @param offsetWalk Offset to set, if the user walks (in minutes)
	 */
	public void setOffsetWalk(int offsetWalk) {
		this.offsetWalk = offsetWalk;
	}
	
	/**
	 * @param offsetSprint Offset to set, if the user sprints (in minutes)
	 */
	public void setOffsetSprint(int offsetSprint) {
		this.offsetSprint = offsetSprint;
	}
	
	/**
	 * @param maxWait Time to set, the user wants to wait at most at the station (in minutes)
	 */
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	
	@Override
	public String toString() {
		return "Configuration [connection=" + connection.toString() + ",offsetWalk=" + offsetWalk
				+ ",offsetSprint=" + offsetSprint + ",maxWait=" + maxWait + "]";
	}
	
}
