package retrieval;

import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
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
	private static ArrayList<User> allUsers;
	private static PagableResponseList<User> users;
	private static String outputFile = "output2.txt";
	private static Writer out;

	public static void main(String[] args) {
//		followingUsers = new HashMap<Long, String>();
		
		try {
			out = new BufferedWriter(new FileWriter(outputFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int requests = 0;
		try {
			Twitter twitter = new TwitterFactory().getInstance();
			allUsers = new ArrayList<User>();
			
			//while there is a next page, get users
			long cursor = -1; //start with page one, cursor as -1
			do {
				users = twitter.getFriendsList(17461978, cursor);
				requests++;
				allUsers.addAll(users);
				cursor = users.getNextCursor();
			}
			while (users.hasNext());
			
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to delete status: " + te.getMessage());
//			System.exit(-1);
		}
		finally {
			System.out.println("Requests made: " + requests);
		}
		System.out.println("Done parsing following");
		Iterator<User> it = allUsers.iterator();
		while (it.hasNext()) {
			User user = (User) it.next();
			System.out.println(user.getName());
			System.out.println(user.getId());
			System.out.println(user.getURL());
			System.out.println();
			
			try {
				out.write(user.getName() + ", " + user.getURL() + '\n');
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	
	
	
	
	
}
