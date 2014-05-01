package analysis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;

import twitter4j.JSONException;
import twitter4j.JSONObject;


public class TriadicClosure {

    /** class fields */
    private static final String JSON_FILE = "adjacencyList.json";


    public static void main(String[] args) {

        // open and read json file
        try {
            InputStream is = new FileInputStream(JSON_FILE);
            String jsonTxt = IOUtils.toString(is);
            JSONObject json = new JSONObject(jsonTxt);
            Iterator it = json.keys();
            
            int i = 0;
            while (it.hasNext()) {
                String id = (String) it.next();
                System.out.println("Twitter user " + i++ + " : " + id);
            }
       
            is.close();
        } catch (FileNotFoundException e) {
            System.out.println("Internal error: " 
                    + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Internal error: " 
                    + e.getMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            System.out.println("Internal error: " 
                    + e.getMessage());
            e.printStackTrace();
        }

    }

}
