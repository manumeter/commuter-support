package ch.zhaw.mas8i.pendlersupport.model;

/**
 * Exception will be thrown if there is no connection data
 * available for the given connection.
 */
public class NoConnectionException extends Exception {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	
	/**
	 * Constructor needs the connection that caused this
	 * exception.
	 * 
	 * @param connection Connection that caused this exception
	 */
	public NoConnectionException(Connection connection) {
		super();
		this.connection = connection;
	}
	
	/**
	 * @return Connection that caused this exception
	 */
	public Connection getConnection() {
		return connection;
	}
}
