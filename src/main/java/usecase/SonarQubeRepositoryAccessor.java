package usecase;
import org.json.JSONArray;

import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
public class SonarQubeRepositoryAccessor {
    private InputStream is;
    private InputStreamReader isr;
    private HttpURLConnection httpConnection;
    private Map<String, String> properties;

    public SonarQubeRepositoryAccessor(){
        properties = new HashMap<>();
    }

    public JSONArray httpGet(String url) throws IOException {
//        String userCredentials = "admin:islab1221";
        String userCredentials = "admin:admin";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

        httpConnection = getConnection(url);
        httpConnection.setRequestProperty ("Authorization", basicAuth);
        httpConnection.setRequestMethod("GET");
        setConnectionProperty(httpConnection);
        BufferedReader reader = getJSONUsingHttpsGet(httpConnection);
        String completeContent = getCompleteContentString(reader);
        if(completeContent.charAt(0) != '[') completeContent = "[" + completeContent + "]";
        JSONArray jsonArray = new JSONArray(completeContent);
        closeAllConnection();
        return jsonArray;
    }


    public void addHTTPSGetProperty(String property, String value){
        this.properties.put(property, value);
    }

    private HttpURLConnection getConnection(String url) throws IOException {
        URL requestUrl = new URL(url);
        return (HttpURLConnection) requestUrl.openConnection();
    }

    private void setConnectionProperty(HttpURLConnection conn){
        for(String property : this.properties.keySet())
            conn.setRequestProperty(property, this.properties.get(property));
    }

    private BufferedReader getJSONUsingHttpsGet(HttpURLConnection httpConnection) throws IOException {
        is = httpConnection.getInputStream();
        isr = new InputStreamReader(is, "UTF-8");
        return new BufferedReader(isr);
    }

    private String getCompleteContentString(BufferedReader reader) throws IOException {
        StringBuilder compeleteContent = new StringBuilder(0);
        String line = "";
        while ((line = reader.readLine()) != null) compeleteContent.append(line);
        return compeleteContent.toString();
    }

    private void closeAllConnection() throws IOException {
        is.close();
        isr.close();
        httpConnection.disconnect();
    }
}
