package ch.zhaw.mas8i.pendlersupport.model;

/**
 * Exception will be thrown if anything went wrong with
 * the connection to the data source (TransportData) or if
 * the received data is invalid.
 */
public class TransportDataException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor needs the throwable that caused this
	 * exception.
	 * 
	 * @param cause Throwable that caused this Exception
	 */
	public TransportDataException(Throwable cause) {
		super(cause);
	}
}
