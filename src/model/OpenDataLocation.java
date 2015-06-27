package ch.zhaw.mas8i.pendlersupport.model;

import java.util.List;

/**
 * Location class to match the JSON result from OpenData
 * Web API. Will only be used in the OpenData class.
 */
public class OpenDataLocation {
	private List<Stations> stations;

	public List<Stations> getStations() {
		return this.stations;
	}

	public void setStations(List<Stations> stations) {
		this.stations = stations;
	}

	@Override
	public String toString() {
		return "Location [stations=" + stations + "]";
	}

	public class Stations {
		private Coordinate coordinate;
		private String distance;
		private String id;
		private String name;
		private String score;

		public Coordinate getCoordinate() {
			return this.coordinate;
		}

		public void setCoordinate(Coordinate coordinate) {
			this.coordinate = coordinate;
		}

		public String getDistance() {
			return this.distance;
		}

		public void setDistance(String distance) {
			this.distance = distance;
		}

		public String getId() {
			return this.id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getScore() {
			return this.score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		@Override
		public String toString() {
			return "Stations [coordinate=" + coordinate + ", distance="
					+ distance + ", id=" + id + ", name=" + name + ", score="
					+ score + "]";
		}
	}

	public class Coordinate {
		private String type;
		private String x;
		private String y;

		public String getType() {
			return this.type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getX() {
			return this.x;
		}

		public void setX(String x) {
			this.x = x;
		}

		public String getY() {
			return this.y;
		}

		public void setY(String y) {
			this.y = y;
		}

		@Override
		public String toString() {
			return "Coordinate [type=" + type + ", x=" + x + ", y=" + y + "]";
		}
	}
}
