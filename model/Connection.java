package ch.zhaw.mas8i.pendlersupport.model;

import java.io.Serializable;

/**
 * Connection between two (start, destination) locations
 * and an optional third location (via).
 */
public class Connection implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Location start;
	private Location destination;
	private Location via;
	
	/**
	 * Constructor to set start and destination without a
	 * via location.
	 * 
	 * @param start Start location to set
	 * @param destination Destination location to set
	 */
	public Connection(Location start, Location destination) {
		this(start, destination, null);
	}
	
	/**
	 * Constructor to set start and destination with a
	 * via location.
	 * 
	 * @param start Start location to set
	 * @param destination Destination location to set
	 * @param via Via location to set
	 */
	public Connection(Location start, Location destination, Location via) {
		this.start = start;
		this.destination = destination;
		this.via = via;
	}
	
	/**
	 * @return Current start location
	 */
	public Location getStart() {
		return start;
	}
	
	/**
	 * @return Current destination location
	 */
	public Location getDestination() {
		return destination;
	}
	
	/**
	 * @return Current via location
	 */
	public Location getVia() {
		return via;
	}
	
	/**
	 * @param start Start location to set
	 */
	public void setStart(Location start) {
		this.start = start;
	}
	
	/**
	 * @param destination Destination location to set
	 */
	public void setDestination(Location destination) {
		this.destination = destination;
	}
	
	/**
	 * @param via Via location to set (use null to unset)
	 */
	public void setVia(Location via) {
		this.via = via;
	}
	
	@Override
	public String toString() {
		String start = getStart().toString();
		String destination = getDestination().toString();
		String via = getVia() == null ? "null" : getVia().toString();
		return "Connection [start=" + start + ",destination=" + destination + ",via=" + via + "]";
	}
}
