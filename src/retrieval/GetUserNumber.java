package retrieval;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;

import twitter4j.JSONException;
import twitter4j.JSONObject;

public class GetUserNumber {
	private static final String JSON_FILE = "adjacencyList.json";
	private static final String QUEUE = "queue.txt";

	public static void main(String[] args) {
		try {
			//used so that users are not duplicated in queue
			ArrayList<Long> currentQueue = new ArrayList<Long>();
			BufferedReader in = new BufferedReader(new FileReader(QUEUE));

			String line = null;
			while ((line = in.readLine()) != null) {
				long user = Long.parseLong(line);
				currentQueue.add(user);
			}
			System.out.println("Queue length: " + currentQueue.size());

			HashSet<Long> allNodes = new HashSet<Long>();
			InputStream is = new FileInputStream(JSON_FILE);
			String jsonTxt = IOUtils.toString(is);

			JSONObject json = new JSONObject(jsonTxt);

			@SuppressWarnings("rawtypes")
			Iterator it = json.keys();

			while (it.hasNext()) {
				String id = (String) it.next();
				allNodes.add(Long.parseLong(id));
			}

			System.out.println("Number of nodes: " + allNodes.size());
			
			is.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
