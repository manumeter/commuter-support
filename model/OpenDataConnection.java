package ch.zhaw.mas8i.pendlersupport.model;

import java.util.List;

/**
 * Connection class to match the JSON result from OpenData
 * Web API. Will only be used in the OpenData class.
 */
public class OpenDataConnection {

	private List<Connections> connections;

	public List<Connections> getConnections() {
		return connections;
	}

	public void setConnections(List<Connections> connections) {
		this.connections = connections;
	}

	public class Connections {

		private Checkpoint from;
		private Checkpoint to;
		private Checkpoint via;
		private String duration;

		public Checkpoint getFrom() {
			return from;
		}

		public void setFrom(Checkpoint from) {
			this.from = from;
		}

		public Checkpoint getTo() {
			return to;
		}

		public void setTo(Checkpoint to) {
			this.to = to;
		}

		public Checkpoint getVia() {
			return via;
		}

		public void setVia(Checkpoint via) {
			this.via = via;
		}

		public String getDuration() {
			return duration;
		}

		public void setDuration(String duration) {
			this.duration = duration;
		}

	}

	public class Checkpoint {
		private OpenDataLocation station;
		private String arrival;
		private String departure;
		private String plattform;

		public OpenDataLocation getStation() {
			return station;
		}

		public void setStation(OpenDataLocation station) {
			this.station = station;
		}

		public String getArrival() {
			return arrival;
		}

		public void setArrival(String arrival) {
			this.arrival = arrival;
		}

		public String getDeparture() {
			return departure;
		}

		public void setDeparture(String departure) {
			this.departure = departure;
		}

		public String getPlattform() {
			return plattform;
		}

		public void setPlattform(String plattform) {
			this.plattform = plattform;
		}

	}

}
