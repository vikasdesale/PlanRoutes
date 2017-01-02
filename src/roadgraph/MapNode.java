package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

public class MapNode extends GeographicPoint  implements Comparable<MapNode>{

	private List<MapEdge> edges = new ArrayList<>();

	private double timeFromStartNode;
	private double straightLineTimeToGoalNodeAtHighwayLimit;

	public MapNode(double latitude, double longitude) {
		super(latitude, longitude);
	}

	public static MapNode fromGeographicPoint(GeographicPoint point) {
		return new MapNode(point.x, point.y);
	}

	void addEdge(MapNode to, String roadName, String roadType, double length) {
		edges.add(new MapEdge(to, roadName, roadType, length));
	}

	List<MapEdge> getEdges() {
		return edges;
	}

	int numOfEdges() {
		return edges.size();
	}
	public double getTimeFromStartNode() {
		return timeFromStartNode;
	}

	void setTimeFromStartNode(double timeFromStartNode) {
		this.timeFromStartNode = timeFromStartNode;
	}

	void setStraightLineTimeToGoalNodeAtHighwayLimit(GeographicPoint goal) {
		this.straightLineTimeToGoalNodeAtHighwayLimit = distance(goal) / MapEdge.DEFAULT_HIGHWAY_SPEED;
	}

	void setStraightLineTimeToGoalNodeAtHighwayLimit(double distance) {
		this.straightLineTimeToGoalNodeAtHighwayLimit = distance / MapEdge.DEFAULT_HIGHWAY_SPEED;
	}

	@Override
	public int compareTo(MapNode o) {
		// TODO Auto-generated method stub
		double result = (timeFromStartNode + straightLineTimeToGoalNodeAtHighwayLimit)
				- (o.timeFromStartNode + o.straightLineTimeToGoalNodeAtHighwayLimit);
		return result < 0 ? -1 : result == 0 ? 0 : 1;	
	}
	

}
