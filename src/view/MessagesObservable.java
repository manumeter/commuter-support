package ch.zhaw.mas8i.pendlersupport.view;

/**
 * The Observable for Messages, used to send a message
 * to a UI component.
 */
interface MessagesObservable {
	
	/**
	 * @param observer The observer to add
	 */
	void addObserver(Messages observer);
	
	/**
	 * @param observer The observer to remove
	 */
	void removeObserver(Messages observer);
	
	/**
	 * @param e The exception to notify all observers about
	 */
	void notifyObservers(Exception e);
}
