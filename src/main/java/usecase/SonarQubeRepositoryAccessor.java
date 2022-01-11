package usecase;

import org.json.JSONArray;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.Base64;

public class SonarQubeRepositoryAccessor extends Accessor{

    public JSONArray httpGet(String url) throws IOException {
        String userCredentials = "admin:admin";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

        Accessor<HttpsURLConnection> accessor = new Accessor<>();
        accessor.httpConnection = getConnection(url);

        accessor.httpConnection = getConnection(url);
        accessor.httpConnection.setRequestProperty ("Authorization", basicAuth);
        accessor.httpConnection.setRequestMethod("GET");

        setConnectionProperty(accessor.httpConnection);
        BufferedReader reader = getJSONUsingHttpsGet(accessor.httpConnection);
        String completeContent = getCompleteContentString(reader);
        if(completeContent.charAt(0) != '[') completeContent = "[" + completeContent + "]";
        JSONArray jsonArray = new JSONArray(completeContent);
        closeAllConnection();
        return jsonArray;
    }
}
