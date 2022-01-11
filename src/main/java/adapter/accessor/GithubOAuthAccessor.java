package adapter.accessor;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.util.HashMap;

public class GithubOAuthAccessor extends Accessor{

    protected  Accessor<HttpsURLConnection> accessor;

    public GithubOAuthAccessor(){
        accessor = new Accessor<>();
        properties = new HashMap<>();
    }

    public JSONArray httpsGet(String url) throws IOException {

        accessor.connection = getConnection(url);
        accessor.connection.setRequestMethod("GET");

        setConnectionProperty(accessor.connection);
        BufferedReader reader = getJSONUsingHttpsGet(accessor.connection);
        String completeContent = getCompleteContentString(reader);
        if(completeContent.charAt(0) != '[') completeContent = "[" + completeContent + "]";
        JSONArray jsonArray = new JSONArray(completeContent);
        closeAllConnection();
        return jsonArray;
    }

    public JSONObject httpPost(String url, String paramsJSON) throws IOException {
        accessor.connection = getConnection(url);
        accessor.connection.setRequestMethod("POST");
        accessor.connection.setRequestProperty("Content-Type", "application/json");
        accessor.connection.setRequestProperty("Accept", "application/json");
        accessor.connection.setRequestProperty("Access-Control-Allow-Origin", "github.com.*");
        accessor.connection.setDoOutput(true);
        accessor.connection.setReadTimeout(60000);
        accessor.connection.setConnectTimeout(60000);

        accessor.setOutputStream(paramsJSON);

        setConnectionProperty(accessor.connection);


        BufferedReader reader = getJSONUsingHttpsGet(accessor.connection);
        String completeContent = getCompleteContentString(reader);
        if(completeContent.charAt(0) != '[') completeContent = "[" + completeContent + "]";
        JSONArray jsonArray = new JSONArray(completeContent);
        closeAllConnection();
        return jsonArray.getJSONObject(0);
    }
}