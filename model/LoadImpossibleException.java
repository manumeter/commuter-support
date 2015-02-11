package ch.zhaw.mas8i.pendlersupport.model;

/**
 * Exception will be thrown if anything went wrong
 * while loading the configuration.
 */
public class LoadImpossibleException extends Exception {
	private static final long serialVersionUID = 1L;
	private String source;

	/**
	 * Constructor needs the throwable that caused this exception
	 * and a source string.
	 * 
	 * @param cause Throwable that caused this Exception
	 * @param source Source string to describe what the source to load is (file name, db connection, ...)
	 */
	public LoadImpossibleException(Throwable cause, String source) {
		super(cause);
		this.source = source;
	}
	
	/**
	 * @return Source string to describe what the source to load is (file name, db connection, ...)
	 */
	public String getSource() {
		return source;
	}
}
