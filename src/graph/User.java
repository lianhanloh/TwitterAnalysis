package graph;

/**
 * This class represents a twitter user
 * 
 * @author lianhanloh and natchan
 *
 */

public class User {

    /** class fields */
    private final int id;
    
    public User(int id) {
        this.id = id;
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
