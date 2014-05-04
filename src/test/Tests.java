package test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.*;

import graph.*;
import analysis.*;

public class Tests {

    @Test 
    public void testTriadicClosure() {
        User user1 = new User(1);
        User user2 = new User(2);
        HashSet<User> following = new HashSet<User>();
        HashSet<User> followers = new HashSet<User>();
        User user3 = new User(3);
        following.add(user1);
        following.add(user2);
        following.add(user3);
        followers.add(user1);
        followers.add(user2);
        followers.add(user3);
        user1.setFollowers(followers);
        user1.setFollowing(following);
        user2.setFollowers(followers);
        user2.setFollowing(following);
        user3.setFollowers(followers);
        user3.setFollowing(following);
        assertTrue(user2.isConnectedTo(user3));
        Set<User> allUsers = new HashSet<User>();
        allUsers.add(user1);
        allUsers.add(user2);
        allUsers.add(user3);
        TriadicClosure test = new TriadicClosure(allUsers);
        assertEquals("there should be 1 triangle", 1, test.triangleNumber());
        
    }
}
