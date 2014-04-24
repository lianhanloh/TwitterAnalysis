package retrieval;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.simple.parser.JSONParser;

import twitter4j.IDs;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;
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

	//	private static ArrayList<User> allUsers;
	//	private static PagableResponseList<User> users;
	//	private static String outputFile = "output2.txt";
	//	private static Writer out;
	private static final String NODE_LIST = "nodeList.txt";
	private static final String JSON_FILE = "userTweets.json";

	public static void main(String[] args) {
		
		JSONParser parser = new JSONParser();
		JSONObject json = new JSONObject();
		try {
			JSONArray tweets = new JSONArray();
			tweets.put("hello world!");
			tweets.put("boooo");
			json.put("123", tweets);
			
			tweets.put("foo");
			tweets.put("bar");
			json.put("321", tweets);
			FileWriter out = new FileWriter(JSON_FILE);
			out.write(json.toString());
			out.flush();
			out.close();
			
			
			
		} catch (JSONException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(json);
		
		/*
		BufferedReader in = null;
		

		try {
			in = new BufferedReader(new FileReader(NODE_LIST));

			String line = null;
			while ((line = in.readLine()) != null) {
				long userID = Long.parseLong(line.trim());
				Twitter twitter = new TwitterFactory().getInstance();
				ResponseList<Status> statuses = 
						twitter.getUserTimeline(userID);
				


			}


		} catch (IOException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		*/


		/*
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

		 */

	}






}
