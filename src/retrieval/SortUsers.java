package retrieval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Finds the unique users from edge list, then sorts the user IDs
 * and prints them to an external text file
 * 
 * @author NatChan and lianhanloh
 *
 */
public class SortUsers {

	private static final String EDGE_LIST = "edgeList.txt";
	private static final String NODE_LIST = "nodeList.txt";

	public static void main(String[] args) {
		System.out.println("Sorting nodes from " + EDGE_LIST);
		HashSet<Long> userSet = new HashSet<Long>();
		
		HashMap<Long, Long> map = new HashMap<Long, Long>();

		BufferedReader in = null;
		BufferedWriter out = null;

		try {
			in = new BufferedReader(new FileReader(EDGE_LIST));
			out = new BufferedWriter(new FileWriter(NODE_LIST));

			String line;
			while ((line = in.readLine()) != null) {
				String[] split = line.split(" ");
				if (split.length != 2) {
					in.close();
					out.close();
					throw new IllegalArgumentException();
				}
				long from = Long.parseLong(split[0]);
				long to = Long.parseLong(split[1]);
				
				map.put(from, to);

				//add users to set
				userSet.add(from);
				userSet.add(to);
			}
			System.out.println("Map size: " + map.size());

			//sort the list of users
			System.out.println("Sorting...");
			long startTime = System.currentTimeMillis();
			List<Long> list = new ArrayList<Long>(userSet);
			Collections.sort(list);
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			System.out.println("User sort complete");
			System.out.println("Sort took " + Long.toString(duration) + "ms");

			//print user list to text file
			for (long x: list) {
				out.write(Long.toString(x));
				out.newLine();
			}
			System.out.println("Nodes written to " + NODE_LIST);



		} catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


	}

}
