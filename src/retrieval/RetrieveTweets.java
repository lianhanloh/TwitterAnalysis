package retrieval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;

import twitter4j.JSONException;
import twitter4j.JSONObject;
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
	private static final String USER_JSON = "adjacencyListTest.json";
	private static final String NODE_LIST = "nodeListTest.txt";
	private static final String JSON_FILE = "userTweetsTest.json";

	public static void main(String[] args) {
		BufferedReader in = null;

		try {
			in = new BufferedReader(new FileReader(NODE_LIST));

			JSONObject json = null;
			long userID = 0;

			//if running for first time, no tweets have been pulled yet
			if (in.read() == -1) {
				InputStream is = new FileInputStream(JSON_FILE);
				String jsonTxt = IOUtils.toString(is);

				json = new JSONObject(jsonTxt);

				Iterator it = json.keys();

				//writer for printing the queue
				BufferedWriter outNodes = 
						new BufferedWriter(new FileWriter(NODE_LIST));

				//take the first node to pull tweets for
				userID = Long.parseLong((String) it.next());
				while (it.hasNext()) {
					outNodes.write((String) it.next());
					outNodes.newLine();
				}
				outNodes.close();
			}
			
			//already existing json file for tweets
			else {
				in.close();
				in = new BufferedReader(new FileReader(NODE_LIST));
				//get user ID
				userID = Long.parseLong(in.readLine().trim());
				
				//remove user from queue
				BufferedWriter out = new BufferedWriter(new FileWriter(NODE_LIST));
				String line = null;
				while ((line = in.readLine()) != null) {
					out.write(line);
					out.newLine();
				}
				
				InputStream is = new FileInputStream(USER_JSON);
				String jsonTxt = IOUtils.toString(is);

				json = new JSONObject(jsonTxt);
			}
			
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
			
			//put tweets and hashtags into json
			json.put(Long.toString(userID), tweets);
			json.put(Long.toString(userID), hashtags);
			
			int indentFactor = 1;
			String prettyprintedJSON = json.toString(indentFactor);
			FileWriter out = new FileWriter(JSON_FILE);
			out.write(prettyprintedJSON);
			out.flush();
			out.close();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}

	}

}
