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
    private static Set<User> allUsers = new HashSet<User>();

    public Graph() {
    	
    	InputStream is;
		try {
			is = new FileInputStream("testAdjacency.json");
			String jsonTxt = IOUtils.toString(is);
			
			JSONObject json = new JSONObject(jsonTxt);
			
			addUsers(jsonTxt);
			addEdges(jsonTxt);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
    	/*

        // open and read json file
        try {
            InputStream isOne = new FileInputStream(JSON_FILE);
            String jsonTxtOne = IOUtils.toString(isOne);
            InputStream isTwo = new FileInputStream(JSON_FILE_LH);
            String jsonTxtTwo = IOUtils.toString(isTwo);
            addUsers(jsonTxtOne);
            addUsers(jsonTxtTwo);
            addEdges(jsonTxtOne);
            addEdges(jsonTxtTwo);
            isOne.close();
            isOne.close();
        } catch (FileNotFoundException e) {
            System.out.println("Internal error: " 
                    + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Internal error: " 
                    + e.getMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            System.out.println("Internal error: " 
                    + e.getMessage());
            e.printStackTrace();
        }*/
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
            
            System.out.println(id);
            
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
                boolean check = followers.add(follower);
                System.out.println(check);
                if (!check) {
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
                System.out.println(allUsers.contains(friend));
                if (!allUsers.contains(friend)) {
                    continue;
                }
                if (!following.add(friend) ) {
                    throw new RuntimeException("Repeat friend at user "
                            + id + ": " + "friend number: " + j);
                }
            }
            user.setFollowing(following);
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
        Graph graph = new Graph();
        Set<User> g = graph.getGraph();
        System.out.println("Number of users:" + g.size());
        for (User x: g) {
        	System.out.println("ID: " + x.getID());
        	System.out.println("Followers:");
        	System.out.println(x.getFollowers().size());
        	for (User i: x.getFollowers())
        		System.out.println(i.getID());
        	
        	System.out.println("Following:");
        	for (User j: x.getFollowing())
        		System.out.println(j.getID());
        }
    }

}
