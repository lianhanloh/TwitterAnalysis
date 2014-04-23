package retrieval;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterObjectFactory;
import twitter4j.User;
import twitter4j.json.DataObjectFactory;

public class RetrieveUserTweet {

	private static final long USER = 807095;
	
	public static void main(String[] args) {
		
		int requests = 0;
		try {
//			Twitter twitter = new TwitterFactory().getInstance();
//			
//			//while there is a next page, get users
//			long cursor = -1; //start with page one, cursor as -1
//			User user = twitter.showUser(USER);
//			System.out.println(user.toString());
			
			Twitter unauthenticatedTwitter = new TwitterFactory().getInstance();
			//First param of Paging() is the page number, second is the number per page (this is capped around 200 I think.
			Paging paging = new Paging(1, 100);
			List<Status> statuses = 
					unauthenticatedTwitter.getUserTimeline(USER, paging);
			
			Status status = statuses.get(0);
			//Status To JSON String
			String statusJson = TwitterObjectFactory.getRawJSON(status);

			//JSON String to JSONObject
			JSONObject JSON_complete = new JSONObject(statusJson);
			

//			//We get another JSONObject
//			JSONObject JSON_user = JSON_complete.getJSONObject("user");
//
//			//We get a field in the second JSONObject
//			String languageTweet = JSON_user.getString("lang");
			
			
//			System.out.println(languageTweet);
			
//			for (Status s: statuses) {
//				System.out.println();
//				System.out.println(s.getCreatedAt());
//				System.out.println(s.getText());
//			}
			
			
			
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to delete status: " + te.getMessage());
//			System.exit(-1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
//			System.out.println("Requests made: " + requests);
		}
		
	}

}
