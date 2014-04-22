package retrieval;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 * 
 * This program retrieves tweets from Twitter using the twitter4j API
 * 
 * @author natchan and lianhanloh
 *
 */
public class RetrieveTweets {
	
//	private static HashMap<Long, String> followingUsers;
	private static PagableResponseList<User> users;

	public static void main(String[] args) {
//		followingUsers = new HashMap<Long, String>();
		try {
			Twitter twitter = new TwitterFactory().getInstance();
			users = twitter.getFriendsList("natc221", (long) -1);
//			for (long x: ids.getIDs()) {
//				followingUsers.put(x, twitter.showUser(x).getName());
////				System.out.println(twitter.showUser(x).getName());
//			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to delete status: " + te.getMessage());
//			System.exit(-1);
		}
		System.out.println("Done parsing following");
		Iterator it = users.iterator();
		while (it.hasNext()) {
			User user = (User) it.next();
			System.out.println(user.getName());
		}
		
		
		
//		Collection<Entry<Long, String>> names = followingUsers.entrySet();
//		Iterator<Entry<Long, String>> it = names.iterator();
//		while (it.hasNext()) {
//			Entry<Long, String> entry = (Entry<Long, String>) it.next();
//			System.out.println(entry.getKey() + ": " + entry.getValue());
//		}
	}

	
	
	
	
	
}
