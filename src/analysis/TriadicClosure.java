package analysis;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import graph.Graph;
import graph.User;

/**
 * This class provides methods to analyze and/or work with twitter user data in 
 * a graph representation
 * @author Nathaniel Chan and Lianhan Loh
 *
 */

public class TriadicClosure {

    static class Friend implements Comparable<Friend> {

        public long id;
        public int count;
        public Friend (long id, int count) {
            this.id = id;
            this.count = count;
        }
        
        @Override
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

    /**
     * Given a set of users and a target user, prints out the top x recommended
     * friends based on number of strong/weak mutual friends. A friendship is
     * strong if two users follow each other, and weak if only one of the users 
     * follows the other.
     * @param allUsers set of all users in graph 
     * @param id target user
     * @param strong true if only strong friendships are counted, false if 
     * weak friendships are included too
     * @param num number of friend recommendations to make
     */
    public static void friendRecommendation(Set<User> allUsers, 
    		long id, boolean strong, int num) {
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
        // print list of friends
        int i = 0;
        for (Friend f: potentials) {
            if (i >= num) break;
            System.out.println("Friend " + f.id + " has " + f.count 
                    + " mutual friends with " + id);
            i++;
        }
        if (potentials.size() == 0) {
            System.out.println("There are no friend recommendations for " + id);
        }
    }

    public static void main(String[] args) {
    	/*
    	System.out.println("Setting up graph...");
    	long start = System.nanoTime();
    	Graph graph = new Graph();
        Set<User> g = graph.getGraph();
    	long end = System.nanoTime();

        System.out.println("Number of triangles: " + triangleNumber(g));
        System.out.println("Number of users: " + g.size());
        System.out.println("Took " + (end-start) + "ns to setup");
        friendRecommendation(g, 12, false, 5);
        */
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
