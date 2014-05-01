package graph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.IOUtils;

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
    private static Set<User> allUsers = new HashSet<User>();
    
    public Graph() {

        // open and read json file
        try {
            InputStream is = new FileInputStream(JSON_FILE);
            String jsonTxt = IOUtils.toString(is);
            JSONObject json = new JSONObject(jsonTxt);
            @SuppressWarnings("rawtypes")
            Iterator it = json.keys();
            
            int i = 0;
            while (it.hasNext()) {
                String id_string = (String) it.next();
                long id = Long.parseLong(id_string);
                User user = new User(id);
                HashSet<User> following = new HashSet<User>();
                HashSet<User> followers = new HashSet<User>();
//                JSONArray followersJSON = 
                System.out.println("Twitter user " + i++ + " : " + id);
                
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


}
