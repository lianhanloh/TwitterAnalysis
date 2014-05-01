package retrieval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;

import twitter4j.IDs;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class RetrieveFriendsJSON {

	private static final String JSON_FILE = "adjacencyList.json";
	private static final String QUEUE = "queue.txt";
	private static final long START_ID = 17461978;

	public static void main(String[] args) {
		BufferedReader in = null;
		BufferedWriter outQueue = null;

		//used so that users are not duplicated in queue
		ArrayList<Long> currentQueue = new ArrayList<Long>();
		try {
			in = new BufferedReader(new FileReader(QUEUE));

			String line = null;
			while ((line = in.readLine()) != null) {
				long user = Long.parseLong(line);
				currentQueue.add(user);
			}
			//get friends of head of queue
			long userID = START_ID;
			if (currentQueue.size() != 0) {
				userID = currentQueue.get(0);
			}
			in.close();

			System.out.println("Getting followers and following for " + userID);

			HashSet<Long> allNodes = new HashSet<Long>();

			@SuppressWarnings("resource")
			FileReader file = new FileReader(JSON_FILE);

			JSONObject json = null;
			//if JSON file is empty
			if (file.read() == -1) {
				json = new JSONObject();
			}
			//read from existing JSON file
			else {
				InputStream is = new FileInputStream(JSON_FILE);
				String jsonTxt = IOUtils.toString(is);

				json = new JSONObject(jsonTxt);

				@SuppressWarnings("rawtypes")
				Iterator it = json.keys();
				
				while (it.hasNext()) {
					String id = (String) it.next();
					allNodes.add(Long.parseLong(id));
				}
			}

			Twitter twitter = new TwitterFactory().getInstance();
			JSONObject newUser = new JSONObject();
			JSONArray followingJSON = new JSONArray();

			
			//get friends' (following) IDs and add to edge list and queue
			IDs ids = twitter.getFriendsIDs(userID, -1);
			long[] following = ids.getIDs();
			for (long x: following) {
				followingJSON.put(x);
				
				//if not already in queue
				if (!allNodes.contains(x) && !currentQueue.contains(x)) {
					currentQueue.add(x);
				}
			}

			
			//get followers, add to queue and print to edge list
			IDs followerIDs = twitter.getFollowersIDs(userID, -1);
			JSONArray followersJSON = new JSONArray();
			long[] followers = followerIDs.getIDs();
			for (long x: followers) {
				followersJSON.put(x);

				if (!allNodes.contains(x) && !currentQueue.contains(x)) {
					currentQueue.add(x);
				}
			}

			newUser.put("following", followingJSON);
			newUser.put("followers", followersJSON);

			json.put(Long.toString(userID), newUser);

			int indentFactor = 1;
			String prettyprintedJSON = json.toString(indentFactor);
			FileWriter out = new FileWriter(JSON_FILE);
			out.write(prettyprintedJSON);
			out.flush();
			out.close();

			//writer for printing the queue
			outQueue = 
					new BufferedWriter(new FileWriter(QUEUE));

			//print queue into text file
			if (currentQueue.size() > 0) {
				currentQueue.remove(userID);
			}
			for (long x: currentQueue) {
				outQueue.write(Long.toString(x));
				outQueue.newLine();
			}
			outQueue.close();
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
