package ch.zhaw.mas8i.pendlersupport.view.console;

import ch.zhaw.mas8i.pendlersupport.controller.ConfigurationHandler;
import ch.zhaw.mas8i.pendlersupport.controller.TransportData;
import ch.zhaw.mas8i.pendlersupport.view.ConnectionInfo;
import ch.zhaw.mas8i.pendlersupport.view.Menu;
import ch.zhaw.mas8i.pendlersupport.view.Messages;
import ch.zhaw.mas8i.pendlersupport.view.TrafficLight;
import ch.zhaw.mas8i.pendlersupport.view.UserInterface;

/**
 * That's the implementation of the user interface as a console application (interactive).
 * 
 * Windows users may need to change the encoding to be able to see all Unicode characters
 * correctly, e.g.:
 *   C:\Users\Admin>chcp
 *   Active code page: 437
 *   
 *   C:\Users\Admin>java -Dfile.encoding=IBM437 ...
 */
public class Console extends UserInterface {
	private final ConfigurationHandler configHandler;
	private final Menu menu;
	private final Messages messages;
	private final TrafficLight trafficLight;
	private final ConnectionInfo connectionInfo;
	
	/**
	 * Constructor initiates UI components.
	 * 
	 * @param  data TransportData object
	 * @param  configHandler ConfigurationHandler object
	 */
	public Console(TransportData data, ConfigurationHandler configHandler) {
		super(data, configHandler);
		this.configHandler = configHandler;
		this.menu = new ConsoleMenu(data, configHandler);
		this.messages = new ConsoleMessages(menu);
		this.trafficLight = new ConsoleTrafficLight();
		this.connectionInfo = new ConsoleConnectionInfo();
	}
	
	/**
	 * Displays the info header with a lock to System.out
	 * and loads the configuration if wanted.
	 */
	@Override
	public void start() {
		boolean load = false;
		
		synchronized (System.out) {
			System.out.println("======================================");
			System.out.println("  PENDLERSUPPORT: Connection tracker  ");
			System.out.println("======================================");
			System.out.println("  GREEN  ~ There is a connection soon, you have enough time");
			System.out.println("  ORANGE ~ There is a connection soon, you'll need to hurry");
			System.out.println("  RED    ~ You missed the connection, the next one is far away");
			System.out.println("  Press Ctrl + C to end PENDLERSUPPORT");
			System.out.println();
			
			if (configHandler.hasSaved()) {
				load = ConsoleReader.getBooleanInput("  * Do you want to load the config from file", load);
			}
		}
		
		if (load) {
			configHandler.load();
		}
		else {
			menu.run();
		}
	}
	
	@Override
	public Messages getMessages() {
		return messages;
	}

	@Override
	public TrafficLight getTrafficLight() {
		return trafficLight;
	}
	
	@Override
	public ConnectionInfo getConnectionInfo() {
		return connectionInfo;
	}

}
