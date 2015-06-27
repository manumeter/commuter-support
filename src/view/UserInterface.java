package ch.zhaw.mas8i.pendlersupport.view;

import ch.zhaw.mas8i.pendlersupport.controller.ConfigurationHandler;
import ch.zhaw.mas8i.pendlersupport.controller.ConnectionTracker;
import ch.zhaw.mas8i.pendlersupport.controller.TransportData;

/**
 * Abstract class for all user interfaces to define
 * and register its components.
 */
public abstract class UserInterface {
	private final ConfigurationHandler configHandler;
	private final ConnectionTracker tracker;
	
	/**
	 * Constructor for all user interface objects.
	 * 
	 * @param data TransportData object
	 * @param configHandler ConfigurationHandler object
	 */
	public UserInterface(TransportData data, ConfigurationHandler configHandler) {
		this.configHandler = configHandler;
		this.tracker = ConnectionTracker.getInstance(data, configHandler);
	}
	
	/**
	 * Start respectively show the user interface.
	 */
	public abstract void start();
	
	/**
	 * @return ConnectionInfo object as a component of this user interface
	 */
	protected abstract ConnectionInfo getConnectionInfo();
	
	/**
	 * @return the Messages object as a component of this user interface
	 */
	protected abstract Messages getMessages();
	
	/**
	 * @return the TrafficLight object as a component of this user interface
	 */
	protected abstract TrafficLight getTrafficLight();
	
	/**
	 * Register all user interface components in the respective observable objects.
	 */
	public final void registerComponents() {
		tracker.addObserver(getMessages());
		tracker.addObserver(getTrafficLight());
		configHandler.addObserver(getMessages());
		configHandler.addObserver(getConnectionInfo());
	}
}