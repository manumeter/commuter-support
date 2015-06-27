package ch.zhaw.mas8i.pendlersupport.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.mas8i.pendlersupport.Log;
import ch.zhaw.mas8i.pendlersupport.model.Configuration;
import ch.zhaw.mas8i.pendlersupport.model.Connection;
import ch.zhaw.mas8i.pendlersupport.model.LoadImpossibleException;
import ch.zhaw.mas8i.pendlersupport.model.SaveImpossibleException;
import ch.zhaw.mas8i.pendlersupport.view.ConnectionInfo;
import ch.zhaw.mas8i.pendlersupport.view.Messages;

/**
 * That's the implementation of ConfigurationHandler which offers a
 * permanent storage into a serialized file (handed over in the constructor).
 */
public class ConfigurationSerializedFile implements ConfigurationHandler {
	private static ConfigurationSerializedFile instance = null;
	private Configuration config;
	private final List<Messages> messageObservers;
	private final List<ConnectionInfo> connectionObservers;
	private File file;
	
	private ConfigurationSerializedFile(String filePath) {
		messageObservers = new ArrayList<>();
		connectionObservers = new ArrayList<>();
		file = new File(filePath);
	}
	
	/**
	 * @param filePath Path to the file to use for configuration save/load
	 * @return The only existing instance of ConfigurationSerializedFile or a new one if there is no existing yet
	 */
	public static synchronized ConfigurationHandler getInstance(String filePath) {
		if (instance == null) {
			instance = new ConfigurationSerializedFile(filePath);
		}
		return instance;
	}
	
	/**
	 * Removes the only instance of ConfigurationSerializedFile (for testing purpose).
	 */
	public static void clearInstance() {
		instance = null;
	}
	
	/**
	 * All ConnectionInfo observers will be notified about the new configuration.
	 * 
	 * @param config The configuration to set
	 */
	@Override
	public void setConfiguration(Configuration config) {
		Log.info("Setting new Configuration, notifying Observers: " + connectionObservers);
		this.config = config;
		notifyObservers(config.getConnection());
	}
	
	/**
	 * @return The current configuration
	 */
	@Override
	public Configuration getConfiguration() {
		return config;
	}
	
	/**
	 * Saves the current configuration to a serialized file. May
	 * notify all Messages observers if an exception gets thrown.
	 */
	@Override
	public void save() {
		Log.info("Saving Configuration to: " + file);
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(config);
		}
		catch (NullPointerException | IOException e) {
			notifyObservers(new SaveImpossibleException(e, file.getPath()));
			Log.warning(e.getMessage());
		}
	}

	/**
	 * Loads the configuration from a serialized file. May notify
	 * all Messages observers if an exception gets thrown.
	 */
	@Override
	public void load() {
		Log.info("Loading Configuration from: " + file);
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			config = (Configuration) ois.readObject();
			notifyObservers(config.getConnection());
		} catch (ClassNotFoundException | IOException e) {
			notifyObservers(new LoadImpossibleException(e, file.getPath()));
			Log.warning(e.getMessage());
		}
	}

	/**
	 * @return True if file exists, otherwise false
	 */
	@Override
	public boolean hasSaved() {
		return file.exists();
	}

	/**
	 * @return True if configuration is set, otherwise false
	 */
	@Override
	public boolean hasLoaded() {
		return config != null;
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

	@Override
	public void addObserver(ConnectionInfo observer) {
		connectionObservers.add(observer);
	}

	@Override
	public void removeObserver(ConnectionInfo observer) {
		connectionObservers.remove(observer);
	}

	@Override
	public void notifyObservers(Connection connection) {
		for(ConnectionInfo observer : connectionObservers) {
			observer.setConnection(connection);
		}
	}

}
