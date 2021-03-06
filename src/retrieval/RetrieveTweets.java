package retrieval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
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
 * Retrieves tweets of a particular user, and appends it in a json file
 * 
 * @author Nathaniel Chan (natc221)
 * @author LianHan Loh
 *
 */
public class RetrieveTweets {

	//will not overwrite the original adjacency list json file
	private static final String ORIGINAL_JSON_FILE = "adjacencyList.json";
	
	//node list is initially a list of all the nodes, but then acts as a queue
	//as the program is run
	private static final String NODE_LIST = "nodeList.txt";
	private static final String USER_JSON = "userTweets.json";

	public static void main(String[] args) {
		BufferedReader in = null;

		try {
			in = new BufferedReader(new FileReader(NODE_LIST));

			JSONObject json = null;
			long userID = 0;

			//if running for first time, no tweets have been pulled yet
			if (in.read() == -1) {
				//get JSON file from adjacency list
				InputStream is = new FileInputStream(ORIGINAL_JSON_FILE);
				String jsonTxt = IOUtils.toString(is);

				json = new JSONObject(jsonTxt);

				@SuppressWarnings("rawtypes")
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
				in.close();
			}
			
			//already existing json file for tweets
			else {
				in.close();
				ArrayList<String> users = new ArrayList<String>();
				in = new BufferedReader(new FileReader(NODE_LIST));
				//get user ID
				userID = Long.parseLong(in.readLine().trim());
				
				//remove user from queue
				String line = null;
				while ((line = in.readLine()) != null) {
					users.add(line.trim());
				}
				in.close();
				
				//write new queue to node list
				BufferedWriter out = new BufferedWriter(new FileWriter(NODE_LIST));
				for (String x: users) {
					out.write(x);
					out.newLine();
				}
				out.close();
				
				//get JSON file and make JSON object
				InputStream is = new FileInputStream(USER_JSON);
				String jsonTxt = IOUtils.toString(is);
				is.close();

				json = new JSONObject(jsonTxt);
			}
			
			//get user from json object
			JSONObject currentUser = json.getJSONObject(Long.toString(userID));
			System.out.println("Getting tweets for:" + userID);
			
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
			
			//put tweets and hashtags into user json object
			currentUser.put("tweets", tweets);
			currentUser.put("hashtags", hashtags);
			
			//put user back into greater json object
			json.put(Long.toString(userID), currentUser);
			
			//write the json object to file
			int indentFactor = 1;
			String prettyprintedJSON = json.toString(indentFactor);
			FileWriter out = new FileWriter(USER_JSON);
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
