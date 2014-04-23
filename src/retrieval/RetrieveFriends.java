package retrieval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 * Takes in a member id as an argument, then writes the
 * list of friends to one output .txt, and that its connected
 * to these friends in an edge list.
 *
 */
public class RetrieveFriends {

	private static final String QUEUE = "queue.txt";
	private static final String EDGE_LIST = "edgeList.txt";

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Wrong number of arguments:");
			System.out.println("Please input member ID");
			System.out.println("Exiting...");
			System.exit(-1);
		}
		HashSet<Long> currentQueue = new HashSet<Long>();

		try {
			BufferedReader in = new BufferedReader(new FileReader(QUEUE));

			String line = null;
			while ((line = in.readLine()) != null) {
				long user = Long.parseLong(in.readLine());
				currentQueue.add(user);
			}

			//create writers that will write at end of file and append
			BufferedWriter outEdge = 
					new BufferedWriter(new FileWriter(EDGE_LIST, true));
			BufferedWriter outQueue = 
					new BufferedWriter(new FileWriter(QUEUE, true));

			//user ID is argument
			long userID = Long.parseLong(args[0]);

			Twitter twitter = new TwitterFactory().getInstance();

			//get friends' IDs
			IDs ids = null;
			do {
				System.out.println("No. of friends: " + 
						twitter.showUser(userID).getFriendsCount());

				ids = twitter.getFriendsIDs(userID, -1);
				long[] list = ids.getIDs();
				System.out.println("List length:" + list.length);
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

			outEdge.close();
			outQueue.close();
		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}


	}

}
