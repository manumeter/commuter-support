package ch.zhaw.mas8i.pendlersupport.view.console;

import ch.zhaw.mas8i.pendlersupport.model.ConnectionStatus;
import ch.zhaw.mas8i.pendlersupport.model.Time;
import ch.zhaw.mas8i.pendlersupport.view.TrafficLight;

/**
 * That's the implementation of TrafficLight for the console.
 * It displays the current status as one line to System.out.
 */
public class ConsoleTrafficLight implements TrafficLight {

	@Override
	public void setStatus(ConnectionStatus status, Time nextTime) {
		if (nextTime == null) {
			System.out.println(">> Connection status is " + status.name());
		}
		else {
			String time = nextTime.toString();
			String seconds = Integer.toString(nextTime.getDiffSeconds(new Time()));
			String color = status.name();
			System.out.println(">> Connection at " + time + " is " + color + " (" + seconds + " seconds left)");
		}
	}

}
