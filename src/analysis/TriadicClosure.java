package analysis;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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
     * followers and following. Ignores the direction of the edge, if false is
     * given an argument. Only considers relationships where both users follow
     * each other when true is given.
     * @param allUsers set of users.
     * @param strong true if only strong relationships are counted, false if
     * weak relationships are included. 
     * @return number of triangles in graph
     */
    public static int triangleNumber(Set<User> allUsers, boolean strong) {
        Set<Set<User>> triangles = new HashSet<Set<User>>();

        for (User user: allUsers) {
            HashSet<User> connected = user.getFollowers();
            connected.addAll(user.getFollowing());

            for (User x: connected) {
                for (User y: connected) {
                    if (x.equals(y)) continue;
                    if (strong) {
                        if (x.isStrongFriend(y)) {
                            Set<User> currentTriang = new HashSet<User>();
                            currentTriang.add(user);
                            currentTriang.add(x);
                            currentTriang.add(y);
                            triangles.add(currentTriang);
                        }
                    }
                    else if (x.isConnectedTo(y)) {
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
        System.out.println("Getting " + num + " friend recommendations for" 
                + " Twitter user " + id);
        // create set of potential friends
        TreeSet<Friend> potentials = new TreeSet<Friend> ();
        int count = 0;
        for (User u: allUsers) {
            if (u.getID() == id) continue;
            HashSet<User> connected = u.getFollowers();
            connected.addAll(u.getFollowing());
            // skip if u is already a friend of id
            if (strong) {
                if (u.isStrongFriend(user)) {
                    continue;
                }
            } else {
                if (u.isConnectedTo(user)) {
                    continue;
                }
            }
            for (User f: connected) {
                // count number of strong mutual friends if strong is specified
                if (strong) {
                    if (f.isStrongFriend(u) && f.isStrongFriend(user)) {
                        count++;
                    }
                }
                // else include weak friends
                else {
                    // by definition, f is already weakly connected to u
                    if (f.isConnectedTo(user)) { 
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
            System.out.println("Twitter user " + f.id + ":\t\t(" + f.count 
                    + " mutual friends)");
            i++;
        }
        if (potentials.size() == 0) {
            System.out.println("There are no friend recommendations for " + id);
        }
    }

    /**
     * Given a set of users in a graph representation, provides num top
     * recommendations for a target to user to follow. Follow recommendations
     * are made by seeing who the target user's friends are following.
     * @param allUsers set of all users
     * @param id target user's id
     * @param num number of recommendations to make
     */
    public static void followRecommendations(Set<User> allUsers, long id,
            int num) {
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
        System.out.println("Getting " + num + " follow recommendations for" 
                + " Twitter user " + id);
        // create set of potential recommendations
        TreeSet<Friend> potentials = new TreeSet<Friend> ();
        int count = 0;
        HashSet<User> connected = user.getFollowers();
        connected.addAll(user.getFollowing());
        for (User u: allUsers) {
            if (u.getID() == id) continue;
            // skip if user already follows u
            if (user.follows(u)) {
                continue;
            }
            for (User f: connected) {
                // count number of user's friends who follow u
                if (f.follows(u)) {
                    count++;
                }
            }
            // add to set of potential recommendations if user's friends follow
            // him/her
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
            System.out.println("Twitter user " + f.id + ":\t\t(" + f.count 
                    + " friends follow him/her)");
            i++;
        }
        if (potentials.size() == 0) {
            System.out.println("There are no follow recommendations for " + id);
        }

    }
}
