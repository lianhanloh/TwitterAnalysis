package retrieval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

	public static void main(String[] args) {
		if (args.length != 0) {
			System.out.println("Wrong number of arguments:");
			System.out.println("Please input member ID");
			System.out.println("Exiting...");
			System.exit(-1);
		}
		//used so that users are not duplicated in queue
		ArrayList<Long> currentQueue = new ArrayList<Long>();

		try {
			
			BufferedReader in = new BufferedReader(new FileReader(QUEUE));

			String line = null;
			while ((line = in.readLine()) != null) {
				long user = Long.parseLong(line);
				currentQueue.add(user);
			}
			//get friends of head of queue
			long userID = 0;
			if (currentQueue.size() != 0) {
				userID = currentQueue.get(0);
			}
			else {
				userID = START_ID;
			}
			System.out.println("Collecting friends of " + userID);
			
			//create writers that will write at end of file and append
			BufferedWriter outEdge = 
					new BufferedWriter(new FileWriter(EDGE_LIST, true));
			BufferedWriter outQueue = 
					new BufferedWriter(new FileWriter(QUEUE));
			
			currentQueue.remove(0);
			for (long x: currentQueue) {
				outQueue.write(Long.toString(x));
				outQueue.newLine();
			}


			Twitter twitter = new TwitterFactory().getInstance();

			//get friends' IDs
			IDs ids = null;
			do {
				ids = twitter.getFriendsIDs(userID, -1);
				long[] list = ids.getIDs();
				for (long x: list) {
					String xID = Long.toString(x);

					//if not already in queue
					if (!currentQueue.contains(x)) {
						outQueue.write(xID);
						outQueue.newLine();
					}
					outEdge.write(userID + " " + xID);
					outEdge.newLine();
				}
			}
			while (ids.hasNext());
			
			System.out.println("Process complete.");

			//close streams
			outQueue.close();
			outEdge.close();
			in.close();
		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
