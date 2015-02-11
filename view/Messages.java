package ch.zhaw.mas8i.pendlersupport.view;

/**
 * This is a UI component, Observer for MessagesObservable
 * and used to send messages to the user (interface).
 */
public interface Messages {
	
	/**
	 * @param e Exception to be handled by the UI
	 */
	void setMessage(Exception e);
}
