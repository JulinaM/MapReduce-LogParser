package utils;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by jmaharjan on 12/21/16.
 */
public class ServerReputation {
    private static final String URL = "http://api.mywot.com/0.4/public_link_json2";
    private static final String API_KEY = "c54db10dc620b53ea624da30ce78effb07540e83";

    private static final Set<String> MALICIOUS_CODES = new HashSet<String>(Arrays.asList("101", "102", "103", "104", "105"));
    private String request;
    private Set<String> categories;
    private String host;

    private void generateRequest(){
        request = "curl -XGET " + URL + "?hosts=" + host + "/&callback=process&key=" + API_KEY;
    }

    public void checkReputation(String ipAddress){
        this.host = ipAddress;
        generateRequest();
        try {
            StringWriter writer = new StringWriter();
            Process p = Runtime.getRuntime().exec(request);
            System.out.println("REQUEST:  "+ request);
            p.waitFor();
            if (p.exitValue() != 0) {
                System.out.println("Server "+ URL + " is down.");
                throw new RuntimeException("Error when connecting to server.");
            }
            IOUtils.copy(p.getInputStream(), writer, "UTF-8");
            String response = writer.toString();
            response = response.replaceAll("process\\(","");
            response = response.replaceAll("\\)", "");
            parse(new JSONObject(response));
            System.out.println("RESPONSE: "+ response);

        } catch (IOException e) {
            throw new RuntimeException("Error while executing command: " + e.getLocalizedMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException("Error while executing command: " + e.getLocalizedMessage());
        } catch (JSONException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    private void parse(JSONObject jsonObject){
        //jsonObject :: {"www.ianfette.org":{"target":"ianfette.org","0":[60,13],"1":[60,13],"2":[60,13],"categories":{"501":13,"304":4}}}
        try {
            JSONObject parentObject = jsonObject.getJSONObject(host);
            JSONObject categories = (parentObject.has("categories")) ? parentObject.getJSONObject("categories") : new JSONObject();
            this.categories = new HashSet<String>(categories.length());
            Iterator iterator = categories.keys();
            while (iterator.hasNext()){
                String key =  (String) iterator.next();
                this.categories.add(key);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public boolean isBlackList(){
        for (String category : categories) {
            if(MALICIOUS_CODES.contains(category)){
                return true;
            }
        }
        return false;
    }
}
