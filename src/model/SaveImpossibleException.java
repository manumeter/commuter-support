package ch.zhaw.mas8i.pendlersupport.model;

/**
 * Exception will be thrown if anything went wrong
 * while saving the configuration.
 */
public class SaveImpossibleException extends Exception {
	private static final long serialVersionUID = 1L;
	private String destination;
	
	/**
	 * Constructor needs the throwable that caused this exception
	 * and a destination string.
	 * 
	 * @param cause Throwable that caused this Exception
	 * @param destination Destination string to describe what the destination to save is (file name, db connection, ...)
	 */
	public SaveImpossibleException(Throwable cause, String destination) {
		super(cause);
		this.destination = destination;
	}
	
	/**
	 * @return Destination string to describe what the destination to save is (file name, db connection, ...)
	 */
	public String getDestination() {
		return destination;
	}
}
