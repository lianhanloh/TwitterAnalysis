package graph;

import java.util.HashSet;

/**
 * This class represents a twitter user
 * 
 * @author lianhanloh and natchan
 *
 */

public class User {

    /** class fields */
    private final long id;
    private HashSet<User> followers;
    private HashSet<User> following;
    
    public User(long id) {
        this.id = id;
        this.followers = new HashSet<User>();
        this.following = new HashSet<User>();
    }
    
    /** returns user's ID */
    public long getID() {
        return id;
    }
    
    /** returns the set of users this user follows */
    public HashSet<User> getFollowing () {
        return following;
    }
    
    /** returns the set of users following this user */
    public HashSet<User> getFollowers() {
        return followers;
    }

    /** assigns to a user the set of users he/she follows */
    public void setFollowing(HashSet<User> following) {
        this.following = following;
    }
    
    /** assigns to a user the set of users who follow him/her */
    public void setFollowers(HashSet<User> followers) {
        this.followers = followers;
    }
    
    /** returns true if either user follows the other */
    public boolean isConnectedTo(User user) {
    	return followers.contains(user) || following.contains(user);
    }
    
    /** returns true if this user is following the user given as an argument */
    public boolean follows(User user) {
        return following.contains(user);
    }
    
    /** returns true if both users follow each other */
    public boolean isStrongFriend(User user) {
        return followers.contains(user) && following.contains(user);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
    	return (int) (41 * this.id);
    }
    
    public static void main(String[] args) {
        User user2 = new User(2);
        HashSet<User> following = new HashSet<User>();
        HashSet<User> followers = new HashSet<User>();
        User user3 = new User(3);
        following.add(user3);
        followers.add(user2);
        user2.setFollowers(followers);
        user2.setFollowing(following);
        System.out.println(user2.isConnectedTo(user3));
        
    }
}
