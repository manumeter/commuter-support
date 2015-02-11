package ch.zhaw.mas8i.pendlersupport.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import ch.zhaw.mas8i.pendlersupport.Log;
import ch.zhaw.mas8i.pendlersupport.model.Connection;
import ch.zhaw.mas8i.pendlersupport.model.OpenDataConnection;
import ch.zhaw.mas8i.pendlersupport.model.OpenDataLocation;
import ch.zhaw.mas8i.pendlersupport.model.Location;
import ch.zhaw.mas8i.pendlersupport.model.Time;
import ch.zhaw.mas8i.pendlersupport.model.TransportDataException;
import ch.zhaw.mas8i.pendlersupport.model.OpenDataConnection.Connections;
import ch.zhaw.mas8i.pendlersupport.model.OpenDataLocation.Stations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Thats the implementation of TransportData for OpenData (http://opendata.ch/). It uses
 * OpenDataConnection and OpenDataLocation for representation of the JSON query results.
 */
public class OpenData implements TransportData {
	private static OpenData instance = null;
	private final static String API = "http://transport.opendata.ch/v1/";
	private final static String ENCODING = "UTF-8";

	private OpenData() {
		// Only one instance of this class should exist (Singleton), so getInstance() must be used.
	}
	
	/**
	 * @return The only existing instance of OpenData or a new one if there is no existing yet
	 */
	public static OpenData getInstance() {
		if (instance == null) {
			instance = new OpenData();
		}
		return instance;
	}
	
	/**
	 * Removes the only instance of OpenData (for testing purpose).
	 */
	public static void clearInstance() {
		instance = null;
	}
	
	/**
	 * Builds a URL from the given connection data and time. Uses GsonBuilder to
	 * parse the result (JSON) into a Java object (OpenDataConnection). The relevant
	 * data will then be returned as a SortedSet of Time objects.
	 * 
	 * @param connection The connection (start, end and optionally via locations) to query for
	 * @param time All results must be after that time
	 * @return A set of existing connection times for the given credentials returned from OpenData
	 * @throws TransportDataException If anything went wrong with the connection to OpenData or the result is invalid
	 */
	@Override
	public SortedSet<Time> getTimeList(Connection connection, Time time) throws TransportDataException {
		Location from = connection.getStart();
		Location to = connection.getDestination();
		Location via = connection.getVia();

		String url = API + "connections?from=" + urlEncode(from.getName()) + "&to=" + urlEncode(to.getName());
		if (via != null) {
			url += "&via=" + urlEncode(via.getName());
		}
		url += "&date=" + time.toString("yyyy-MM-dd") + "&time=" + time.toString("HH:mm");
		
		SortedSet<Time> timeList = new TreeSet<>();
		Gson gson = new GsonBuilder().create();
		
		Log.info("Connecting to OpenData: " + url);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
			OpenDataConnection data = gson.fromJson(reader, OpenDataConnection.class);
			for (Connections c : data.getConnections()) {
				timeList.add(new Time().setFromString(c.getFrom().getDeparture(), "yyyy-MM-dd'T'HH:mm:ssZ"));
			}
		}
		catch (IOException | ParseException e) {
			throw new TransportDataException(e);
		}
		
		return timeList;
	}

	/**
	 * Builds a URL from the given string. Uses GsonBuilder to parse the result (JSON)
	 * into a Java object (OpenDataLocation). The relevant data will then be returned
	 * as a List of possible Location objects.
	 * 
	 * @param locationName A search string for locations
	 * @return A list of existing locations matching the given string
	 * @throws TransportDataException If anything went wrong with the connection to OpenData or the result is invalid
	 */
	@Override
	public List<Location> getPossibleLocationList(String locationName) throws TransportDataException {

		List<Location> listLocation = new ArrayList<>();
		String url = API + "locations?query=" + urlEncode(locationName);
		Gson gson = new GsonBuilder().create();

		Log.info("Connecting to OpenData: " + url);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
			OpenDataLocation locations = gson.fromJson(reader, OpenDataLocation.class);
			for (Stations station : locations.getStations()) {
				listLocation.add(new Location(station.getName()));
			}
		}
		catch (IOException e) {
			throw new TransportDataException(e);
		}
		
		return listLocation;
	}

	private String urlEncode(String url) {
		try {
			url = URLEncoder.encode(url, ENCODING);
		}
		catch (UnsupportedEncodingException e) {
			// Returns url unencoded if unsupported encoding configured!
			Log.error(e.getMessage());
		}
		return url;
	}
	
}
