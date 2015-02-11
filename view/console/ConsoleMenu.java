package ch.zhaw.mas8i.pendlersupport.view.console;

import ch.zhaw.mas8i.pendlersupport.controller.ConfigurationHandler;
import ch.zhaw.mas8i.pendlersupport.controller.TransportData;
import ch.zhaw.mas8i.pendlersupport.model.Configuration;
import ch.zhaw.mas8i.pendlersupport.model.Connection;
import ch.zhaw.mas8i.pendlersupport.model.Location;
import ch.zhaw.mas8i.pendlersupport.view.Menu;

/**
 * That's the implementation of Menu for the console.
 */
public class ConsoleMenu implements Menu {
	private final TransportData data;
	private final ConfigurationHandler configHandler;
	
	/**
	 * Constructor needs TransportData and ConfigurationHandler.
	 * 
	 * @param data TransportData object
	 * @param configHandler ConfigurationHandler object
	 */
	public ConsoleMenu(TransportData data, ConfigurationHandler configHandler) {
		this.data = data;
		this.configHandler = configHandler;
	}
	
	/**
	 * Displays the menu and locks System.out to avoid message
	 * display while the menu is running.
	 */
	@Override
	public void run() {
		boolean save = false;
		Configuration config = null;
		
		synchronized (System.out) {
			System.out.println("----------------------");
			System.out.println("  Configuration menu  ");
			System.out.println("----------------------");
			
			Location start = ConsoleReader.getLocationInput(data, "Start", false);
			Location destination = ConsoleReader.getLocationInput(data, "Destination", false);
			Location via = ConsoleReader.getLocationInput(data, "Via", true);
			int offsetWalk = ConsoleReader.getIntegerInput("  * Time (minutes) you normally need to the start location");
			int offsetSprint = ConsoleReader.getIntegerInput("  * Time (minutes) you need if you hurry");
			int maxWait = ConsoleReader.getIntegerInput("  * Time (minutes) you want to wait at most at the start location");
			save = ConsoleReader.getBooleanInput("  * Do you want to save this config", true);
			config = new Configuration(new Connection(start, destination, via), offsetWalk, offsetSprint, maxWait);
			configHandler.setConfiguration(config);
		}
		
		if (save) {
			configHandler.save();
		}
	}
}