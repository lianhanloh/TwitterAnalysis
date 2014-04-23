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
			System.out.println("Wrong number of arguments");
			System.exit(-1);
		}
		HashSet<Long> currentQueue = new HashSet<Long>();
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(QUEUE));
			
			String line = null;
			do {
				long user = Long.parseLong(in.readLine());
				currentQueue.add(user);
			}
			while (line != null);
			
			//create writers that will write at end of file and append
			Writer outEdge = 
					new BufferedWriter(new FileWriter(EDGE_LIST, true));
			Writer outQueue = 
					new BufferedWriter(new FileWriter(QUEUE, true));

			//user ID is argument
			long userID = Long.parseLong(args[0]);

			Twitter twitter = new TwitterFactory().getInstance();
			List<Long> friendsIDs = new ArrayList<Long>();

			//get friends' IDs
			IDs ids = null;
			do {
				ids = twitter.getFriendsIDs(userID, -1);
				long[] list = ids.getIDs();
				for (long x: list) {
					//if not already in queue
					if (!currentQueue.contains(x)) {
						outQueue.write(Long.toString(x));
					}
					//TODO
					String edge = Long.toString(x);
					friendsIDs.add(x);
				}
			}
			while (ids.hasNext());

		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}


	}

}
