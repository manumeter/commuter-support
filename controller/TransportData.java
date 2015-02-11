package ch.zhaw.mas8i.pendlersupport.controller;

import java.util.List;
import java.util.SortedSet;

import ch.zhaw.mas8i.pendlersupport.model.Connection;
import ch.zhaw.mas8i.pendlersupport.model.Location;
import ch.zhaw.mas8i.pendlersupport.model.Time;
import ch.zhaw.mas8i.pendlersupport.model.TransportDataException;

/**
 * Interface to the data source.
 */
public interface TransportData {
	
	/**
	 * Get a sorted set of time objects for a given connection after the given
	 * time. The set may be empty if no connection exists.
	 * 
	 * @param  connection Containing start, end (and optionally a via location)
	 * @param  time All results must be after that time
	 * @return A set of existing connection times for the given credentials
	 * @throws TransportDataException If anything went wrong with the connection to the data source
	 */
	SortedSet<Time> getTimeList(Connection connection, Time time) throws TransportDataException;
	
	/**
	 * Use this function to search for possible locations matching a search string.
	 * 
	 * @param  locationName A search string for locations
	 * @return A list of existing locations matching the given string
	 * @throws TransportDataException If anything went wrong with the connection to the data source 
	 */
	List<Location> getPossibleLocationList(String locationName) throws TransportDataException;
}
