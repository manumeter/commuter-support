package ch.zhaw.mas8i.pendlersupport.view;

import ch.zhaw.mas8i.pendlersupport.model.ConnectionStatus;
import ch.zhaw.mas8i.pendlersupport.model.Time;

/**
 * This is a UI component, Observer for TrafficLightObservable
 * and used to update the status about the current connection.
 */
interface TrafficLight {
	
	/**
	 * Update the status and time for the current connection.
	 *
	 * @param status The new status
	 * @param nextTime Next departure time for which this status applies
	 */
	void setStatus(ConnectionStatus status, Time nextTime);
}
