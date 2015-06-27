package ch.zhaw.mas8i.pendlersupport.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import ch.zhaw.mas8i.pendlersupport.Log;
import ch.zhaw.mas8i.pendlersupport.model.Configuration;
import ch.zhaw.mas8i.pendlersupport.model.ConnectionStatus;
import ch.zhaw.mas8i.pendlersupport.model.NoConfigurationException;
import ch.zhaw.mas8i.pendlersupport.model.NoConnectionException;
import ch.zhaw.mas8i.pendlersupport.model.Time;
import ch.zhaw.mas8i.pendlersupport.model.TransportDataException;
import ch.zhaw.mas8i.pendlersupport.view.Messages;
import ch.zhaw.mas8i.pendlersupport.view.TrafficLight;
import ch.zhaw.mas8i.pendlersupport.view.TrafficLightObservable;

/**
 * ConnectionTracker is the main controller of this application. Its run method gets
 * executed by a scheduled executor service in a fixed delay rate (REFRESH_RATE). The
 * ConnectionTracker is responsible to check the existence of configuration data, keep
 * the connection data up to date, request new data if needed and notify the UI about
 * the current status.
 */
public class ConnectionTracker implements Runnable, TrafficLightObservable {
	private static ConnectionTracker instance = null;
	private static final int REFRESH_RATE = 3;
	private final List<TrafficLight> statusObservers;
	private final List<Messages> messageObservers;
	private final SortedSet<Time> connections;
	private final TransportData data;
	private final ConfigurationHandler configHandler;
	private ConnectionStatus status;
	private Time time;
	private Configuration lastConfig;
	
	private ConnectionTracker(TransportData data, ConfigurationHandler configHandler) {
		this.statusObservers = new ArrayList<>();
		this.messageObservers = new ArrayList<>();
		this.connections = new TreeSet<>();
		this.data = data;
		this.configHandler = configHandler;
		this.status = ConnectionStatus.UNKNOWN;
		this.time = null;
	}
	
	/**
	 * @param data TransportData object
	 * @param configHandler ConfigurationHandler object
	 * @return The only existing instance of ConnectionTracker or a new one if there is no existing yet
	 */
	public static synchronized ConnectionTracker getInstance(TransportData data, ConfigurationHandler configHandler) {
		if (instance == null) {
			instance = new ConnectionTracker(data, configHandler);
		}
		return instance;
	}
	
	/**
	 * Removes the only instance of ConnectionTracker (for testing purpose).
	 */
	public static void clearInstance() {
		instance = null;
	}
	
	/**
	 * @return Time in seconds, in which the run method of this object should be initiated
	 */
	public static int getRefreshRate() {
		return REFRESH_RATE;
	}
	
	/**
	 * A pass of this method goes through the following points:
	 * - Check if there is a configuration available in the ConfigurationHandler.
	 *   Notify the observers if not and stop this pass.
	 * - Check if the configuration has changed since the last pass. Notify the
	 *   observer if so and stop this pass.
	 * - Check if the temporary stored connection data is still up to date and if
	 *   there are enough connections in the temporary store. If not, try to get
	 *   new data. If there are (still) exceptions after some retries, observers
	 *   get notified and stop this pass.
	 * - Calculate the current status.
	 * - Notify all observers about the current status and time.
	 */
	@Override
	public synchronized void run() {
		// Synchronized the run method to be able to initiate a pass from another
		// thread than the scheduled executor service, e.g. for rapidly initiating
		// a refresh after saving the configuration from menu.
		
		Log.info("ConnectionTracker pass, config: " + lastConfig);
		
		if (!configHandler.hasLoaded()) {
			if (!configHandler.hasSaved()) {
				Log.info("No config available, skip this pass");
				notifyObservers(new NoConfigurationException());
			}
			else {
				Log.info("No config loaded yet, skip this pass");
			}
			status = ConnectionStatus.UNKNOWN;
			time = null;
			notifyObservers(status, time);
			return;
		}
		
		if (lastConfig != configHandler.getConfiguration()) {
			Log.info("Config has changed, skip this pass");
			lastConfig = configHandler.getConfiguration();
			connections.clear();
			status = ConnectionStatus.UNKNOWN;
			time = null;
			notifyObservers(status, time);
			return;
		}
		
		try {
			updateConnections();
		}
		catch (TransportDataException | NoConnectionException e) {
			status = ConnectionStatus.UNKNOWN;
			time = null;
			notifyObservers(status, time);
			notifyObservers(e);
			Log.warning(e.getMessage());
			return;
		}
		
		time = connections.first();
		
		Time nowWalk = new Time().addMinutes(configHandler.getConfiguration().getOffsetWalk());
		Time nowWalkWait = new Time().addMinutes(
			configHandler.getConfiguration().getOffsetWalk() +
			configHandler.getConfiguration().getMaxWait()
		);
		
		if (nowWalkWait.getTime() < time.getTime()) {
			status = ConnectionStatus.RED;
		}
		else if (nowWalk.getTime() < time.getTime()) {
			status = ConnectionStatus.GREEN;
		}
		else {
			status = ConnectionStatus.ORANGE;
		}
		
		notifyObservers(status, time);
	}
	
	private void updateConnections() throws NoConnectionException, TransportDataException {
		Time nextTimeSprint = new Time();
		int countTransportDataExceptions = 0;
		
		while (true) {
			
			if (connections.size() <= 1) {
				
				Time nextConnections = new Time();
				if (connections.isEmpty()) {
					nextConnections.addMinutes(configHandler.getConfiguration().getOffsetSprint());
				}
				else {
					nextConnections.setTime(connections.last().getTime());
				}
				
				try {
					Log.info("Getting new TimeList from TransportData");
					connections.addAll(data.getTimeList(configHandler.getConfiguration().getConnection(), nextConnections));
				}
				catch (TransportDataException e) {
					Log.warning(e.getMessage());
					countTransportDataExceptions++;
					Log.info("Exception number " + countTransportDataExceptions + " of 3");
					if (countTransportDataExceptions > 3) {
						Log.info("Maximum reached, throw exception to calling function");
						throw e;
					}
					else {
						try {
							Log.info("Waiting 0.1 seconds befor retry");
							Thread.sleep(100);
						}
						catch (InterruptedException e1) {
							// No handling needed, it's not an essential sleep.
							Log.info(e.getMessage());
						}
						continue;
					}
				}
				
				if (connections.isEmpty()) {
					throw new NoConnectionException(configHandler.getConfiguration().getConnection());
				}
			}
			
			nextTimeSprint.setTime(connections.first().getTime());
			nextTimeSprint.subtractMinutes(configHandler.getConfiguration().getOffsetSprint());
			
			if (nextTimeSprint.isPassed()) {
				connections.remove(connections.first());
			}
			else {
				break;
			}
		}
	}

	@Override
	public void addObserver(TrafficLight observer) {
		statusObservers.add(observer);
	}

	@Override
	public void removeObserver(TrafficLight observer) {
		statusObservers.remove(observer);
	}

	@Override
	public void notifyObservers(ConnectionStatus status, Time nextTime) {
		for(TrafficLight observer : statusObservers) {
			observer.setStatus(status, nextTime);
		}
	}

	@Override
	public void addObserver(Messages observer) {
		messageObservers.add(observer);
	}

	@Override
	public void removeObserver(Messages observer) {
		messageObservers.remove(observer);
	}

	@Override
	public void notifyObservers(Exception e) {
		for(Messages observer : messageObservers) {
			observer.setMessage(e);
		}
	}
}
