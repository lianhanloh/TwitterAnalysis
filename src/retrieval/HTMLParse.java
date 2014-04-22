package retrieval;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParse {
	
	private static final String inputFile = "output.txt";
	private static HashMap<String, String> allUsers;
	
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
			String URL = allUsers.get("Al Gore");
			Document doc = Jsoup.connect(URL).get();
			
			for (Element x: doc.getAllElements()) {
				System.out.println(x.text());
				
			}
			Elements categoryData = doc.select("[href]");
			System.out.println(categoryData.text());
				
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
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
