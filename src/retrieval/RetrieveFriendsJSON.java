package retrieval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import twitter4j.HashtagEntity;
import twitter4j.IDs;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.ResponseList;
import twitter4j.Status;
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

			JSONParser parser = new JSONParser();
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
			
			Iterator it = json.keys();

//			Set<Entry<String, JsonElement>> set = json.entrySet();
//			Iterator<Entry<String, JsonElement>> it = set.iterator();
			while (it.hasNext()) {
				String id = (String) it.next();
				allNodes.add(Long.parseLong(id));
//				Entry entry = it.next();
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
			
//			Gson gson = new GsonBuilder().setPrettyPrinting().create();
//			String output = gson.fromJson(json, JsonObject.class);
			
			FileWriter out = new FileWriter(JSON_FILE);
			out.write(json.toString(1));
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
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*

			file = new FileReader(JSON_FILE);
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
			Set allUsers = json.keySet();

			//used so that users are not duplicated in queue
			ArrayList<Long> currentQueue = new ArrayList<Long>();

			inQueue = new BufferedReader(new FileReader(QUEUE));
			String line = null;
			while ((line = inQueue.readLine()) != null) {
				long user = Long.parseLong(line);
				currentQueue.add(user);
			}
			//get friends of head of queue
			long userID = START_ID;
			if (currentQueue.size() != 0) {
				userID = currentQueue.get(0);
			}

			Twitter twitter = new TwitterFactory().getInstance();

			//get friends' (following) IDs and add to edge list and queue
			IDs ids = twitter.getFriendsIDs(userID, -1);
			long[] list = ids.getIDs();
			for (long x: list) {

				//if not already in queue
				if (!allUsers.contains(x) && !currentQueue.contains(x)) {
					currentQueue.add(x);
				}
				JSONArray followFollow = (JSONArray) json.get(Long.toString(userID));

			}

			//get followers, add to queue and print to edge list
			IDs followerIDs = twitter.getFollowersIDs(userID, -1);
			long[] list2 = followerIDs.getIDs();
			for (long x: list2) {
				//				String xID = Long.toString(x);

				//if not already in queue
				if (!allUsers.contains(x) && !currentQueue.contains(x)) {
					currentQueue.add(x);
				}
			}





		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}*/
	}

	/*
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

	}*/


}
