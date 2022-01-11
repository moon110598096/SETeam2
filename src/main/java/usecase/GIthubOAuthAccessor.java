package usecase;

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
    @Override
    public JSONArray httpsGet(String url) throws IOException {

        accessor.httpsConnection = (HttpsURLConnection)getConnection(url);
        accessor.httpsConnection.setRequestMethod("GET");

        setConnectionProperty(accessor.httpsConnection);
        BufferedReader reader = getJSONUsingHttpsGet(accessor.httpsConnection);
        String completeContent = getCompleteContentString(reader);
        if(completeContent.charAt(0) != '[') completeContent = "[" + completeContent + "]";
        JSONArray jsonArray = new JSONArray(completeContent);
        closeAllConnection();
        return jsonArray;
    }

    public JSONObject httpPost(String url, String paramsJSON) throws IOException {
        Accessor<HttpsURLConnection> accessor = new Accessor<>();
        accessor.httpsConnection = (HttpsURLConnection)getConnection(url);
        accessor.httpsConnection.setRequestMethod("POST");
        accessor.httpsConnection.setRequestProperty("Content-Type", "application/json");
        accessor.httpsConnection.setRequestProperty("Accept", "application/json");
        accessor.httpsConnection.setRequestProperty("Access-Control-Allow-Origin", "github.com.*");
        accessor.httpsConnection.setDoOutput(true);
        accessor.httpsConnection.setReadTimeout(60000);
        accessor.httpsConnection.setConnectTimeout(60000);

        accessor.setOutputStream(paramsJSON);

        setConnectionProperty(accessor.httpsConnection);


        BufferedReader reader = getJSONUsingHttpsGet(accessor.httpsConnection);
        String completeContent = getCompleteContentString(reader);
        if(completeContent.charAt(0) != '[') completeContent = "[" + completeContent + "]";
        JSONArray jsonArray = new JSONArray(completeContent);
        closeAllConnection();
        return jsonArray.getJSONObject(0);
    }
}