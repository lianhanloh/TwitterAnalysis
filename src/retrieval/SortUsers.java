package retrieval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

public class SortUsers {
	
	private static final String EDGE_LIST = "edgeList.txt";
	private static final String NODE_LIST = "nodeList.txt";

	public static void main(String[] args) {
		ArrayList<Long> userSet = new ArrayList<Long>();
		
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
				
				userSet.add(from);
				userSet.add(to);
			}
			
			Collections.sort(userSet);
			
			for (long x: userSet) {
				out.write(Long.toString(x));
				out.newLine();

			}
			
			
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
