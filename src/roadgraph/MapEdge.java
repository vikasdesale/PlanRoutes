package roadgraph;

public class MapEdge {

		private final MapNode to;
		private final String roadName;
		private final String roadType;
		private final double length;

		//set the values of goal:to, roadName,roadType,length
		public MapEdge(MapNode to, String roadName, String roadType, double length) {
			this.to = to;
			this.roadName = roadName;
			this.roadType = roadType;
			this.length = length;
		}

		//returns Goal
		public MapNode getTo() {
			return to;
		}

		//returns roadName
		public String getRoadName() {
			return roadName;
		}

		//returns roadType
		public String getRoadType() {
			return roadType;
		}

		//returns road length
		public double getLength() {
			return length;
		}

		

	

}
