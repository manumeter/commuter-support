package ch.zhaw.mas8i.pendlersupport.model;

/**
 * Exception will be thrown if there is no configuration.
 */
public class NoConfigurationException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor, no further information needed.
	 */
	public NoConfigurationException() {
		super();
	}
}
