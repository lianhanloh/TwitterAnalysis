package graph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;

/**
 * This class parses a JSON file of twitter users and who they follow and are 
 * their followers to populate a graph representation of users.
 * 
 * @author lianhanloh and natchan
 *
 */

public class Graph {

	/** class fields */
	private static final String JSON_FILE = "adjacencyList.json";
	private static final String JSON_FILE_LH = "adjacencyList_lh.json";
	private Set<User> allUsers = new HashSet<User>();

	/**
	 * Constructor for graph
	 * @param file1 - first json file
	 * @param file2 - second json file, put null if no such file necessary
	 * @throws IOException
	 * @throws JSONException
	 */
	public Graph(String file1, String file2) throws IOException, JSONException {

		// open and read json files
		InputStream isOne = new FileInputStream(file1);
		String jsonTxtOne = IOUtils.toString(isOne);
		InputStream isTwo = new FileInputStream(file2);
		String jsonTxtTwo = IOUtils.toString(isTwo);
		addUsers(jsonTxtOne);
		addUsers(jsonTxtTwo);
		addEdges(jsonTxtOne);
		addEdges(jsonTxtTwo);
		isOne.close();
		isOne.close();
	}

	/**
	 * populates graph by adding followers and following sets only if they
	 * are in the set of all users
	 */
	private void addEdges(String jsonTxt) throws JSONException {
		JSONObject json = new JSONObject(jsonTxt);
		@SuppressWarnings("rawtypes")
		Iterator it = json.keys();

		while (it.hasNext()) {
			String id_string = (String) it.next();
			long id = Long.parseLong(id_string);

			User user = new User(id);
			HashSet<User> following = new HashSet<User>();
			HashSet<User> followers = new HashSet<User>();
			// get user's json object
			JSONObject userJSON = json.getJSONObject(id_string);
			JSONArray followersJSON = userJSON.getJSONArray("followers");
			// get followers and add to hash set
			int numFollowers = followersJSON.length();
			for (int j = 0; j < numFollowers; j++) {
				User follower = new User(followersJSON.getInt(j));
				if (!allUsers.contains(follower)) {
					continue;
				}
				for (User x: allUsers) {
					if (x.equals(follower)) follower = x;
				}
				if (!followers.add(follower)) {
					throw new RuntimeException("Repeat followers at user "
							+ id + ": " + "follower number: " + j);
				}
			}
			user.setFollowers(followers);
			// get following and set to hash set
			JSONArray followingJSON = userJSON.getJSONArray("following");
			int numFollowing = followingJSON.length();
			for (int j = 0; j < numFollowing; j++) {
				User friend = new User(followingJSON.getInt(j));

				if (!allUsers.contains(friend)) {
					continue;
				}
				for (User x: allUsers) {
					if (x.equals(friend)) friend = x;
				}

				if (!following.add(friend) ) {
					throw new RuntimeException("Repeat friend at user "
							+ id + ": " + "friend number: " + j);
				}
			}
			user.setFollowing(following);
			allUsers.remove(new User(id));
			allUsers.add(user);
		}
	}

	/**
	 * given a JSON string, adds all the user ids (keys) to the set of all users
	 */
	private void addUsers(String jsonTxt) throws JSONException {

		JSONObject json = new JSONObject(jsonTxt);
		@SuppressWarnings("rawtypes")
		Iterator it = json.keys();

		while (it.hasNext()) {
			String id_string = (String) it.next();
			long id = Long.parseLong(id_string);
			User user = new User(id);
			// add to set of users
			allUsers.add(user);
		}
	}

	/** returns all the users */
	public Set<User> getGraph() {
		return allUsers;
	}

	public static void main(String[] args) {
		Graph graph;
		try {
			graph = new Graph(JSON_FILE, JSON_FILE_LH);
			Set<User> g = graph.getGraph();
			System.out.println();
			System.out.println("Number of users:" + g.size());
			for (User x: g) {
				System.out.println();
				System.out.println("ID: " + x.getID());
				System.out.println("Followers:");
				for (User i: x.getFollowers())
					System.out.println(i.getID());
				
				System.out.println("Following:");
				for (User j: x.getFollowing())
					System.out.println(j.getID());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
