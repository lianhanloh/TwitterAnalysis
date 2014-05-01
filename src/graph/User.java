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
}
