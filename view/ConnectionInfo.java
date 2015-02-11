package ch.zhaw.mas8i.pendlersupport.view;

import ch.zhaw.mas8i.pendlersupport.model.Connection;

/**
 * This is a UI component, Observer for ConnectionInfoObservable
 * and used to update the current connection.
 */
public interface ConnectionInfo {
	
	/**
	 * @param connection The new active connection, when it has changed
	 */
	void setConnection(Connection connection);
}
