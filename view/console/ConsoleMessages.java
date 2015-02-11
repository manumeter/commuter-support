package ch.zhaw.mas8i.pendlersupport.view.console;

import ch.zhaw.mas8i.pendlersupport.model.LoadImpossibleException;
import ch.zhaw.mas8i.pendlersupport.model.NoConfigurationException;
import ch.zhaw.mas8i.pendlersupport.model.NoConnectionException;
import ch.zhaw.mas8i.pendlersupport.model.SaveImpossibleException;
import ch.zhaw.mas8i.pendlersupport.model.TransportDataException;
import ch.zhaw.mas8i.pendlersupport.view.Menu;
import ch.zhaw.mas8i.pendlersupport.view.Messages;

/**
 * That's the implementation of Messages for the console.
 */
public class ConsoleMessages implements Messages {
	private final Menu menu;
	
	/**
	 * Constructor needs the Menu to be able to display it
	 * if there is no configuration available.
	 * 
	 * @param menu Menu object
	 */
	public ConsoleMessages(Menu menu) {
		this.menu = menu;
	}
	
	@Override
	public void setMessage(Exception ex) {
		try {
			throw ex;
		}
		catch (LoadImpossibleException e) {
			loadImpossible(e);
		}
		catch (SaveImpossibleException e) {
			saveImpossible(e);
		}
		catch (NoConnectionException e) {
			noConnection(e);
		}
		catch (TransportDataException e) {
			noTransportData(e);
		}
		catch (NoConfigurationException e) {
			noConfiguration(e);
		}
		catch (Exception e) {
			System.out.println("An unknown error occured: " + e.getClass().getName());
		}
	}
	
	private void loadImpossible(LoadImpossibleException e) {
		System.out.println("Warning: Could not load configuration from " + e.getSource() + "!");
	}

	private void saveImpossible(SaveImpossibleException e) {
		System.out.println("Error: Could not save configuration to " + e.getDestination() + "!");
	}

	private void noConnection(NoConnectionException e) {
		String connection = "from " + e.getConnection().getStart() + " to " + e.getConnection().getDestination();
		if (e.getConnection().getVia() != null) {
			connection += " via " + e.getConnection().getVia();
		}
		System.out.println("Warning: Could not find a connection " + connection + "!");
		
		menu.run();
	}
	
	private void noTransportData(TransportDataException e) {
		System.out.println("Warning: Currently no connection to the transport data source!");
	}
	
	private void noConfiguration(NoConfigurationException e) {
		System.out.println("No configuration loaded.");
	}

}
