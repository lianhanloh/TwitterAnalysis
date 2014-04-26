package retrieval;

import java.io.BufferedReader;
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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.JsonElement;

import twitter4j.HashtagEntity;
import twitter4j.IDs;
import twitter4j.JSONArray;
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
		BufferedReader inQueue = null;
		JsonParser parser = new JsonParser();

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
			//			InputStream is = new FileInputStream(JSON_FILE);
			//			InputStreamReader isr = new InputStreamReader(is);
			//
			//			//create JsonReader object
			//			JsonReader reader = new JsonReader(isr);
			HashSet<Long> allNodes = new HashSet<Long>();

			FileReader file = new FileReader(JSON_FILE);
			Object obj = parser.parse(file);
			JsonObject json = (JsonObject) obj;

			Set<Entry<String, JsonElement>> set = json.entrySet();
			Iterator<Entry<String, JsonElement>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, JsonElement> entry = it.next();
				allNodes.add(Long.parseLong(entry.getKey()));
			}

			Twitter twitter = new TwitterFactory().getInstance();

			//get friends' (following) IDs and add to edge list and queue
			IDs ids = twitter.getFriendsIDs(userID, -1);
			long[] following = ids.getIDs();
			for (long x: following) {

				//if not already in queue
				if (!allNodes.contains(x) && !currentQueue.contains(x)) {
					currentQueue.add(x);
				}

			}

			//get followers, add to queue and print to edge list
			IDs followerIDs = twitter.getFollowersIDs(userID, -1);
			long[] followers = followerIDs.getIDs();
			JsonArray followerJSON = (JsonArray) followers;//new JsonArray();
			for (long x: followers) {
				if (!allNodes.contains(x) && !currentQueue.contains(x)) {
					currentQueue.add(x);
				}
				followerJSON.add(JsonElement) x);
			}
			
			JsonObject newUser = new JsonObject();
			
			
			newUser.add("following", followerIDs);

			File file2 = new File(JSON_FILE);
			OutputStreamWriter out = 
					new OutputStreamWriter(new FileOutputStream(file2));
			JsonWriter writer = new JsonWriter(out);
			writer.setIndent("\t");

			writer.beginObject();
			writer.name("USERID");

			//inside this user
			writer.beginObject();

			//following
			writer.name("following").beginArray();
			for (long id: following)
				writer.value(id);
			writer.endArray();

			//followers
			writer.name("followers").beginArray();
			for (long id: followers)
				writer.value(id);
			writer.endArray();

			writer.endObject();

			//new user
			writer.name("1234567");
			writer.beginObject();
			writer.name("location").value("Hong Kong");

			writer.endObject();
			writer.endObject();
			writer.flush();
			writer.close();

		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
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
