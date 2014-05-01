package graph;

import java.util.Set;

/**
 * This class represents a twitter user
 * 
 * @author lianhanloh and natchan
 *
 */

public class User {

    /** class fields */
    private final long id;
    private Set<User> followers;
    private Set<User> following;
    
    public User(long id) {
        this.id = id;
    }
    
    /** returns the set of users this user follows */
    public Set<User> getFollowing () {
        return following;
    }
    
    /** returns the set of users following this user */
    public Set<User> getFollowers() {
        return followers;
    }

    /** assigns to a user the set of users he/she follows */
    public void setFollowing(Set<User> following) {
        this.following = following;
    }
    
    /** assigns to a user the set of users who follow him/her */
    public void setFollowers(Set<User> followers) {
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
