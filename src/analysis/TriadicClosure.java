package analysis;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import graph.Graph;
import graph.User;

public class TriadicClosure {

    private Graph graph;
    private Set<User> allUsers;

    /** constructor with set of users */
    public TriadicClosure (Set<User> allUsers) {
        this.allUsers = allUsers;
    }

    /** constructor with graph */
    public TriadicClosure (Graph graph) {
        this.graph = graph;
        this.allUsers = graph.getGraph();
    }

    static class Friend implements Comparable<Friend> {

        public long id;
        public int count;
        public Friend (long id, int count) {
            this.id = id;
            this.count = count;
        }

        public int compareTo(Friend f) {
            return this.count - f.count;
        }
    }

    /**
     * Finds the number of triangles in the graph, based on each user's
     * followers and following. Ignores the direction of the edge.
     * @return number of triangles in graph
     */
    public int triangleNumber() {
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

    public void friendRecommendation(long id, boolean strong) {
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
                        System.out.println("reached");
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
