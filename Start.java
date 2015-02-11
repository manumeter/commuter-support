package ch.zhaw.mas8i.pendlersupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import ch.zhaw.mas8i.pendlersupport.controller.ConfigurationSerializedFile;
import ch.zhaw.mas8i.pendlersupport.controller.ConfigurationHandler;
import ch.zhaw.mas8i.pendlersupport.controller.ConnectionTracker;
import ch.zhaw.mas8i.pendlersupport.controller.OpenData;
import ch.zhaw.mas8i.pendlersupport.controller.TransportData;
import ch.zhaw.mas8i.pendlersupport.view.UserInterface;
import ch.zhaw.mas8i.pendlersupport.view.console.Console;
import ch.zhaw.mas8i.pendlersupport.view.swing.Swing;

/**
 * Starting point of the application, containing the
 * only main method.
 */
public class Start {
	private static boolean startGUI = true;
	
	/**
	 * Sets up all required Objects for the application to run and
	 * does the argument handling. Instances configuration strategy,
	 * transport data, connection tracker (scheduler) and the UI.
	 *
	 * @param args The list of arguments given on the start of this application
	 */
	public static void main(String[] args) {
		// Argument handling
		handleArguments(new ArrayList<String>(Arrays.asList(args)));
		
		// Data sources
		Log.info("Creating TranspotData instance: OpenData");
		TransportData data = OpenData.getInstance();
		String configFile = System.getProperty("user.home") + "/.pendlersupport";
		Log.info("Creating ConfigurationHandler instance: ConfigurationSerializedFile(\"" + configFile + "\")");
		ConfigurationHandler config = ConfigurationSerializedFile.getInstance(configFile);
		
		// Core
		Log.info("Creating ConnectionTracker instance");
		ConnectionTracker tracker = ConnectionTracker.getInstance(data, config);
		
		// UserInterface
		Log.info("Creating new UserInterface: " + (startGUI ? "Swing" : "Console"));
		UserInterface ui = startGUI ? new Swing(data, config) : new Console(data, config);
		Log.info("Registring UserInterface components to Observables and starting it");
	 	ui.registerComponents();
	 	ui.start();
	 	
	 	// Scheduler
	 	Log.info("Starting new SceduledExecutorService to run ConnectionTracker every " + ConnectionTracker.getRefreshRate() + " seconds");
	 	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	 	executor.scheduleWithFixedDelay(tracker, 0, ConnectionTracker.getRefreshRate(), TimeUnit.SECONDS);
	}
	
	private static void handleArguments(List<String> args) {
		if (args.contains("--console")) {
			startGUI = false;
			args.remove("--console");
		}
		
		if (args.contains("--log")) {
			boolean next = false;
			String log = null;
			for (String arg : args) {
				if (next) {
					log = arg;
					switch (log) {
						case "debug":   Log.setLevel(Level.ALL);     break;
						case "warning": Log.setLevel(Level.WARNING); break;
						case "error":   Log.setLevel(Level.SEVERE);  break;
					}
				}
				next = arg.equals("--log");
			}
			args.remove("--log");
			args.remove(log);
		}
		
		if (args.size() > 0) {
			String unrecognized = "Unrecognized option(s):";
			for (String arg : args) {
				unrecognized += " " + arg;
			}
			System.out.println(unrecognized);
			System.out.println("Pendlersupport Usage:");
			System.out.println("   --console                    Start console edition instead of GUI");
			System.out.println("   --log [debug|warning|error]  Logs to STDERR");
			System.exit(0);
		}
	}

}
