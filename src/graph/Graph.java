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

        // open and read json file
        try {
            InputStream is = new FileInputStream(JSON_FILE);
            String jsonTxt = IOUtils.toString(is);
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
                    User follower = new User(followersJSON.getLong(j));
                    if (!followers.add(follower) ) {
                        throw new RuntimeException("Repeat followers at user "
                                + id + ": " + "follower number: " + j);
                    }
                }
                user.setFollowers(followers);
                // get following and set to hash set
                JSONArray followingJSON = userJSON.getJSONArray("following");
                int numFollowing = followingJSON.length();
                for (int j = 0; j < numFollowing; j++) {
                    User friend = new User(followingJSON.getLong(j));
                    if (!following.add(friend) ) {
                        throw new RuntimeException("Repeat friend at user "
                                + id + ": " + "friend number: " + j);
                    }
                }
                user.setFollowing(following);
                // add to set of users
                allUsers.add(user);
            }

            is.close();
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
        }
        try{
            InputStream is = new FileInputStream(JSON_FILE_LH);
            String jsonTxt = IOUtils.toString(is);
            JSONObject json = new JSONObject(jsonTxt);
            @SuppressWarnings("rawtypes")
            Iterator it = json.keys();

            while (it.hasNext()) {
                String id_string = (String) it.next();
                long id = Long.parseLong(id_string);
                User user = new User(id);
                // skip if user has already been added to all users set
                if (allUsers.contains(user)) {
                    continue;
                }
                HashSet<User> following = new HashSet<User>();
                HashSet<User> followers = new HashSet<User>();
                // get user's json object
                JSONObject userJSON = json.getJSONObject(id_string);
                JSONArray followersJSON = userJSON.getJSONArray("followers");
                // get followers and add to hash set
                int numFollowers = followersJSON.length();
                for (int j = 0; j < numFollowers; j++) {
                    User follower = new User(followersJSON.getLong(j));
                    if (!followers.add(follower) ) {
                        throw new RuntimeException("Repeat followers at user "
                                + id + ": " + "follower number: " + j);
                    }
                }
                user.setFollowers(followers);
                // get following and set to hash set
                JSONArray followingJSON = userJSON.getJSONArray("following");
                int numFollowing = followingJSON.length();
                for (int j = 0; j < numFollowing; j++) {
                    User friend = new User(followingJSON.getLong(j));
                    if (!following.add(friend) ) {
                        throw new RuntimeException("Repeat friend at user "
                                + id + ": " + "friend number: " + j);
                    }
                }
                user.setFollowing(following);
                // add to set of users
                allUsers.add(user);
            }

            is.close();
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
        }

    }

    /** returns all the users */
    public Set<User> getGraph() {
        return allUsers;
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
    }

}
