package ch.zhaw.mas8i.pendlersupport.controller;

import ch.zhaw.mas8i.pendlersupport.model.Configuration;
import ch.zhaw.mas8i.pendlersupport.view.ConnectionInfoObservable;

/**
 * A class implementing ConfigurationHandler is responsible to handle 
 * the configuration the following way: It stores the currently active 
 * configuration, providing a getter and setter method therefore. Additionally,
 * it provides a save and a load method to store the configuration permanently.
 * To notify the UI, it's also an Observable for ConnectionInfo, which itself
 * extends the MessagesObservable.
 */
public interface ConfigurationHandler extends ConnectionInfoObservable {

	/**
	 * @param config Configuration to set globally
	 */
	void setConfiguration(Configuration config);
	
	/**
	 * @return The current configuration
	 */
	Configuration getConfiguration();
	
	/**
	 * Store the current configuration permanently
	 */
	void save();
	
	/**
	 * Load the permanently stored configuration
	 */
	void load();
	
	/**
	 * @return True if there is a permanently stored configuration available, otherwise false
	 */
	boolean hasSaved();
	
	/**
	 * @return True if there is set a configuration (via setter or load method), otherwise false
	 */
	boolean hasLoaded();
}
