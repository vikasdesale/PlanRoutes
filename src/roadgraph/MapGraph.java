/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 3
	// Maintain both nodes and edges as you will need to
		// be able to look up nodes by lat/lon or by roads
		// that contain those nodes.
		private int numVertices;
		private int numEdges;
		// Use a Map to map the semantic values of our vertices
		// to the actual vertex objects used in our graph that
		// also contain their edges references.
		private Map<GeographicPoint, MapNode> vertices;
	
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 3
		vertices = new HashMap<>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		return numVertices;
	}
	
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		return new HashSet<>(vertices.values());
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		if (location == null || vertices.containsKey(location))
			return false;

		MapNode locationNode = MapNode.fromGeographicPoint(location);
		vertices.put(locationNode, locationNode);
		numVertices++;

		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 3
		if (!(vertices.containsKey(from) && vertices.containsKey(to)))
			throw new IllegalArgumentException("Both points must be present!");

		vertices.get(from).addEdge(vertices.get(to), roadName, roadType, length);
		numEdges++;
		
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return null;
		}

		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<>();

		if (!bfsSearch(start, goal, parentMap, nodeSearched)) {
			System.out.println("No path exists");
			return null;
		}

		return constructPath(start, goal, parentMap);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return null;
		}

		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<>();
		MapNode startNode = null;

		// Treat Dijkstra's algorithm as special instance of A* where h(n) = 0.
		for (MapNode node : vertices.values()) {
			if (node.equals(start)) {
				startNode = node;
				node.setTimeFromStartNode(0);
			} else
				node.setTimeFromStartNode(Double.POSITIVE_INFINITY);
			node.setStraightLineTimeToGoalNodeAtHighwayLimit(0);
		}

		return aStarSearch(startNode, goal, parentMap, nodeSearched) ? constructPath(start, goal, parentMap) : null;
		
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return null;
		}

		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<>();
		MapNode startNode = null;

		// Prepare vertices for search.
		for (MapNode node : vertices.values()) {
			if (node.equals(start)) {
				startNode = node;
				node.setTimeFromStartNode(0);
			} else
				node.setTimeFromStartNode(Double.POSITIVE_INFINITY);
			node.setStraightLineTimeToGoalNodeAtHighwayLimit(goal);
		}

		return aStarSearch(startNode, goal, parentMap, nodeSearched) ? constructPath(start, goal, parentMap) : null;
		
	}

	private boolean bfsSearch(GeographicPoint start, GeographicPoint goal,
			HashMap<GeographicPoint, GeographicPoint> parentMap, Consumer<GeographicPoint> nodeSearched) {
		HashSet<GeographicPoint> visited = new HashSet<>();
		Queue<GeographicPoint> toExplore = new LinkedList<>();
		toExplore.add(start);

		while (!toExplore.isEmpty()) {
			GeographicPoint current = toExplore.remove();
			nodeSearched.accept(current);
			if (current.equals(goal))
				return true;

			for (MapEdge neighborEdge : getNeighbors(current)) {
				MapNode neighbor = neighborEdge.getTo();
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					toExplore.add(neighbor);
					parentMap.put(neighbor, current);
				}
			}
		}

		return false;
	}

	private List<MapEdge> getNeighbors(GeographicPoint point) {
		return vertices.get(point).getEdges();
	}

	private static List<GeographicPoint> constructPath(GeographicPoint start, GeographicPoint goal,
			HashMap<GeographicPoint, GeographicPoint> parentMap) {
		// reconstruct the path
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		GeographicPoint current = goal;
		while (!current.equals(start)) {
			path.addFirst(current);
			current = parentMap.get(current);
		}
		path.addFirst(start);
		return path;
	}
	
	private boolean aStarSearch(MapNode start, GeographicPoint goal,
			HashMap<GeographicPoint, GeographicPoint> parentMap, Consumer<GeographicPoint> nodeSearched) {
		HashSet<MapNode> visited = new HashSet<>();
		PriorityQueue<MapNode> toExplore = new PriorityQueue<>();

		toExplore.add(start);

		while (!toExplore.isEmpty()) {
			MapNode current = toExplore.remove();
			if (visited.contains(current))
				continue;

			visited.add(current);
			nodeSearched.accept(current);
			if (current.equals(goal))
				return true;

			for (MapEdge neighborEdge : getNeighbors(current)) {
				MapNode neighbor = neighborEdge.getTo();
				if (!visited.contains(neighbor)) {
					double time;
					if ((time = current.getTimeFromStartNode()
							+ neighborEdge.getLength() / neighborEdge.getMaxSpeed()) < neighbor
									.getTimeFromStartNode()) {
						neighbor.setTimeFromStartNode(time);
						toExplore.add(neighbor);
						parentMap.put(neighbor, current);
					}
				}
			}
		}

		return false;
	}
	public static void main(String[] args)
	{
		/*System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
			
		/*MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
	
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		*/
		
		//MapGraph testMap = new MapGraph();
		//GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		/*testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		*/
		
		// A slightly more complex test using real data
		/*testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		*/
		
		/* Use this code in Week 3 End of Week Quiz */
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
