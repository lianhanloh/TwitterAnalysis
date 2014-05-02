package analysis;

import java.util.HashSet;
import java.util.Set;

import graph.Graph;
import graph.User;

public class TriadicClosure {

	private static Graph graph;
	private static Set<User> allUsers;
	
	/**
	 * Finds the number of triangles in the graph, based on each user's
	 * followers and following. Ignores the direction of the edge.
	 * @return number of triangles in graph
	 */
	public static int triangleNumber() {
		Set<Set<User>> triangles = new HashSet<Set<User>>();
		
		for (User user: allUsers) {
			HashSet<User> connected = user.getFollowers();
			connected.addAll(user.getFollowing());
			
			for (User x: connected) {
				for (User y: connected) {
					if (x.equals(y)) continue;
					if (x.isConnectedTo(y)) {
						Set<User> currentTriang = new HashSet<User>();
						currentTriang.add(user);
						currentTriang.add(x);
						currentTriang.add(y);
						triangles.add(currentTriang);
					}
				}
			}
		}
		return triangles.size();
	}
	
	public static void friendRecommendation() {
		
	}
	
	
	public static void main(String[] args) {
		graph = new Graph();
		allUsers = graph.getGraph();
		
		System.out.println("Number of triangles: " + triangleNumber());
	}
   
}
