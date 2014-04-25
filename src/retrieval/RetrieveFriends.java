package retrieval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * @author Nathaniel Chan
 * Takes in a member id as an argument, then writes the
 * list of friends to one output .txt, and that its connected
 * to these friends in an edge list.
 *
 */
public class RetrieveFriends {

	private static final String QUEUE = "queue.txt";
	private static final String EDGE_LIST = "edgeList.txt";
	private static final long START_ID = 17461978;
	
	private static BufferedReader in;
	private static BufferedReader inEdge;
	private static BufferedWriter outEdge;
	private static BufferedWriter outQueue;

	public static void main(String[] args) {
		if (args.length != 0) {
			System.out.println("Wrong number of arguments:");
			System.out.println("Please input member ID");
			System.out.println("Exiting...");
			System.exit(-1);
		}
		//used so that users are not duplicated in queue
		ArrayList<Long> currentQueue = new ArrayList<Long>();
		
		//map of edges
		HashMap<Long, Long> edgeMap = new HashMap<Long, Long>();

		try {
			in = new BufferedReader(new FileReader(QUEUE));
			inEdge = new BufferedReader(new FileReader(EDGE_LIST));
			
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
			
			while ((line = inEdge.readLine()) != null) {
				String[] split = line.split(" ");
				long from = Long.parseLong(split[0].trim());
				long to = Long.parseLong(split[1].trim());
				edgeMap.put(from, to);
			}
			inEdge.close();
			
			HashSet<Long> allUsers = new HashSet<Long>();
			allUsers.addAll(edgeMap.keySet());
			allUsers.addAll(edgeMap.values());
			
			System.out.println("Collecting friends of " + userID);

			//create writer for edge list
			//that will write at end of file and append
			outEdge = 
					new BufferedWriter(new FileWriter(EDGE_LIST, true));
			
			//writer for printing the queue
			outQueue = 
					new BufferedWriter(new FileWriter(QUEUE));

			Twitter twitter = new TwitterFactory().getInstance();

			//get friends' (following) IDs and add to edge list and queue
			IDs ids = twitter.getFriendsIDs(userID, -1);
			long[] list = ids.getIDs();
			for (long x: list) {
				String xID = Long.toString(x);

				//if not already in queue
				if (!allUsers.contains(x) && !currentQueue.contains(x)) {
					currentQueue.add(x);
				}
				outEdge.write(userID + " " + xID);
				outEdge.newLine();
			}

			//get followers, add to queue and print to edge list
			IDs followerIDs = twitter.getFollowersIDs(userID, -1);
			long[] list2 = followerIDs.getIDs();
			for (long x: list2) {
				String xID = Long.toString(x);

				//if not already in queue
				if (!allUsers.contains(x) && !currentQueue.contains(x)) {
					currentQueue.add(x);
				}
				outEdge.write(xID + " " + userID);
				outEdge.newLine();
			}
			
			//print queue into text file
			if (currentQueue.size() > 0) {
				currentQueue.remove(userID);
			}
			for (long x: currentQueue) {
				outQueue.write(Long.toString(x));
				outQueue.newLine();
			}
			
			outQueue.close();
			outEdge.close();
			
			System.out.println("Process complete.");
			
		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
