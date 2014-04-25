package retrieval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import twitter4j.HashtagEntity;
import twitter4j.JSONArray;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * 
 * This program retrieves tweets from Twitter using the twitter4j API,
 * then stores it in a JSON file
 * 
 * @author natchan and lianhanloh
 *
 */
public class RetrieveTweets {

	//node list acts as a queue, of which nodes whose tweets need to be
	//pulled
	private static final String NODE_LIST = "nodeList.txt";
	private static final String JSON_FILE = "userTweets.json";

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		BufferedReader in = null;
		JSONParser parser = new JSONParser();
		try {
			in = new BufferedReader(new FileReader(NODE_LIST));
			String user = in.readLine();

			if (user != null && !user.equals("")) {
				long userID = Long.parseLong(user);
				Twitter twitter = new TwitterFactory().getInstance();
				ResponseList<Status> statuses = 
						twitter.getUserTimeline(userID);

				JSONArray tweets = new JSONArray();
				JSONArray hashtags = new JSONArray();

				//get all tweets and hashtags
				for (Status s: statuses) {
					tweets.put(s.getText());

					//get all the hashtags associated
					for (HashtagEntity tag: s.getHashtagEntities()) {
						hashtags.put(tag.getText());
					}
				}

				FileReader file = new FileReader(JSON_FILE);
				JSONObject json = null;
				//if JSON file is empty
				if (file.read() == -1) {
					json = new JSONObject();
				}
				//read from existing JSON file
				else {
					Object obj = parser.parse(file);
					json = (JSONObject) obj;
				}
				
				//put tweets and hashtags into json
				json.put(user, tweets);
				json.put(user, hashtags);

				FileWriter out = new FileWriter(JSON_FILE);
				out.write(json.toString());
				out.flush();
				out.close();

				System.out.println(json);
			}



		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
