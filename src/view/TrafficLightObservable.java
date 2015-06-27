package ch.zhaw.mas8i.pendlersupport.view;

import ch.zhaw.mas8i.pendlersupport.model.ConnectionStatus;
import ch.zhaw.mas8i.pendlersupport.model.Time;

/**
 * The Observable for TrafficLight, used to inform a UI
 * component if the status about the current connection has changed.
 */
interface TrafficLightObservable extends MessagesObservable {
	
	/**
	 * @param observer The observer to add
	 */
	void addObserver(TrafficLight observer);
	
	/**
	 * @param observer The observer to remove
	 */
	void removeObserver(TrafficLight observer);
	
	/**
	 * @param status The new status of the current connection to notify all observers about
	 * @param nextTime Next departure time for which this status applies
	 */
	void notifyObservers(ConnectionStatus status, Time nextTime);
}
