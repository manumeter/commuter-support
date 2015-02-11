package ch.zhaw.mas8i.pendlersupport.view.console;

import ch.zhaw.mas8i.pendlersupport.Log;
import ch.zhaw.mas8i.pendlersupport.model.Connection;
import ch.zhaw.mas8i.pendlersupport.view.ConnectionInfo;

/**
 * That's the implementation of ConnectionInfo for the console.
 * Prints the connection to Systme.out in an ASCII box.
 */
public class ConsoleConnectionInfo implements ConnectionInfo {

	@Override
	public void setConnection(Connection connection) {
		String start = connection.getStart().getName().toUpperCase();
		String destination = connection.getDestination().getName().toUpperCase();
		String title;
		String line = "";
		
		if (connection.getVia() == null) {
			title = start + " -> " + destination;
		}
		else {
			String via = connection.getVia().getName().toUpperCase();
			title = start + " -> " + via + " -> " + destination;
		}
		
		for(int i = 0; i < title.length(); i++) {
			line += "-";
		}
		
		Log.info("Displaying new connection: " + title);
		
		synchronized (System.out) {
			System.out.println("--" + line  + "--");
			System.out.println("  " + title + "  ");
			System.out.println("--" + line  + "--");
		}
	}

}
