package analysis;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import graph.Graph;
import graph.User;

public class TriadicClosure {

    static class Friend implements Comparable<Friend> {

        public long id;
        public int count;
        public Friend (long id, int count) {
            this.id = id;
            this.count = count;
        }

        public int compareTo(Friend f) {
            return f.count - this.count;
        }
    }

    /**
     * Finds the number of triangles in the graph, based on each user's
     * followers and following. Ignores the direction of the edge.
     * @return number of triangles in graph
     */
    public static int triangleNumber(Set<User> allUsers) {
        Set<Set<User>> triangles = new HashSet<Set<User>>();

        for (User user: allUsers) {
        	System.out.println("Originating from :" + user.getID());
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

    public static void friendRecommendation(Set<User> allUsers, 
    		long id, boolean strong) {
        // get target user
        User user = null;
        for (User u: allUsers) {
            if (u.getID() == id) {
                user = u;
                break;
            }
        }
        // return with error message if user is not in set of all users 
        if (user == null) {
            System.out.println("Error: user is not in database");
            return;
        }
        // create set of potential friends
        TreeSet<Friend> potentials = new TreeSet<Friend> ();
        int count = 0;
        for (User u: allUsers) {
        	if (u.getID() == id) continue;
            HashSet<User> connected = u.getFollowers();
            connected.addAll(u.getFollowing());
            for (User f: connected) {
                // count number of strong mutual friends if strong is specified
                if (strong) {
                    if (f.isStrongFriend(u)) {
                        count++;
                    }
                }
                // else include weak friends
                else {
                    if (f.isConnectedTo(u)) {
                        count++;
                    }
                }

            }
            // add to set of potential friends if there are mutual friends
            if (count > 0) {
                potentials.add(new Friend(u.getID(), count));
                // reset count to 0
                count = 0;
            }
        }
        // sort friends in order
        for (Friend f: potentials) {
            System.out.println("Friend " + f.id + " has " + f.count 
                    + " mutual friends with " + id);
        }
        if (potentials.size() == 0) {
            System.out.println("There are no friend recommendations for " + id);
        }
    }

    public static void main(String[] args) {
    	System.out.println("Setting up graph...");
    	long start = System.nanoTime();
    	Graph graph = new Graph();
        Set<User> g = graph.getGraph();
    	long end = System.nanoTime();

        System.out.println("Number of triangles: " + triangleNumber(g));
        System.out.println("Number of users: " + g.size());
        System.out.println("Took " + (end-start) + "ns to setup");
        friendRecommendation(g, 12, false);
        //        graph = new Graph();
        //        allUsers = graph.getGraph();
        //
        //        System.out.println("Number of triangles: " + triangleNumber());
        //        for (User user : allUsers) {
        //            System.out.println("Friend recommendations for " + user.getID() 
        //                    + ": ");
        //            friendRecommendation(user.getID(), false);
        //            System.out.println("------------------------------------------");
        //            break;
        //        }
    }

}
