package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

public class MapNode extends GeographicPoint {

	private List<MapEdge> edges = new ArrayList<>();

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
	

}
