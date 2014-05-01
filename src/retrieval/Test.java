package retrieval;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Test {

	private static final Logger logger = Logger.getLogger(Test.class.getName());

	//	private static final String JSON_FILE = "/home/visruth/Desktop/Visruth.txt";

	public static void main(String[] args) {

		String jsonText = "{\"215876567\": { \"followers\": [ 2464054938, 772677937]}}";

		try	{
			//			FileWriter out = new FileWriter(JSON_FILE);
			InputStream is = new FileInputStream("jsonTest2.json");
			String jsonTxt = IOUtils.toString(is);

			JSONObject json = new JSONObject(jsonTxt);

			int indentFactor = 1;
			String prettyprintedJSON = json.toString(indentFactor);
			System.out.println(prettyprintedJSON);
			//			out.write(prettyprintedJSON);

		} catch (JSONException e) {
			logger.log(Level.SEVERE, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}