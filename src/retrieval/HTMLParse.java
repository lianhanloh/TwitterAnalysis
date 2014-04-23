package retrieval;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class HTMLParse {

	private static final String inputFile = "output.txt";
	private static HashMap<String, String> allUsers;
	private static HashMap<String, List<Status>> userToStatuses;

	public static void main(String[] args) {
		allUsers = new HashMap<String, String>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(inputFile));
			while (true) {
				String name = in.readLine();
				String URL = in.readLine();
				if (name == null || URL == null) break;

				if (!URL.equals("null")) {
					allUsers.put(name, URL);
					System.out.println();
					System.out.println(name);
					System.out.println(URL);
				}
				in.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {

			Twitter twitter = new TwitterFactory().getInstance();
			Paging paging = new Paging(1, 100);



			Iterator<String> it = allUsers.keySet().iterator();

			while (it.hasNext()) {
				String user = it.next();
				List<Status> statuses = twitter.getUserTimeline(user ,paging);
				if (statuses == null) break;
				userToStatuses.put(user, statuses);
			}
		}
		catch (TwitterException e) {
			e.printStackTrace();
		}
		String URL = allUsers.get("Al Gore");
		Document doc;
		try {
			doc = Jsoup.connect(URL).get();
			for (Element x: doc.getAllElements()) {
				System.out.println(x.text());
				
			}
			Elements categoryData = doc.select("[href]");
			System.out.println(categoryData.text());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}





		//		try {
		//			String url = Main.FACTBOOK_URL;
		//			url = url.substring(0, 59);
		//			url += country.getURL();
		//			System.out.println("URL: " + url);
		//			doc = Jsoup.connect(url).get();
		//
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}

	}
}
