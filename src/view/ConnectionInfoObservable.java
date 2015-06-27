package ch.zhaw.mas8i.pendlersupport.view;

import ch.zhaw.mas8i.pendlersupport.model.Connection;

/**
 * The Observable for ConnectionInfo, used to inform a UI
 * component if the connection has changed.
 */
interface ConnectionInfoObservable extends MessagesObservable {
	
	/**
	 * @param observer The observer to add
	 */
	void addObserver(ConnectionInfo observer);
	
	/**
	 * @param observer The observer to remove
	 */
	void removeObserver(ConnectionInfo observer);
	
	/**
	 * @param connection The new connection to notify all observers about
	 */
	void notifyObservers(Connection connection);
}
