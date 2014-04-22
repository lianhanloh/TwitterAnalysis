package retrieval;

import java.util.Iterator;

import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * 
 * This program retrieves tweets from Twitter using the twitter4j API
 * 
 * @author natchan and lianhanloh
 *
 */
public class RetrieveTweets {

	public static void main(String[] args) {
		try {
			Twitter twitter = new TwitterFactory().getInstance();
			IDs ids = 
					twitter.getFriendsIDs("natc221", (long) -1);
			for (long x: ids.getIDs()) {
				System.out.println(twitter.showUser(x).getName());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to delete status: " + te.getMessage());
			System.exit(-1);
		}
	}

}
