package adapter.accessor;

import org.json.JSONArray;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;

public class GithubRepositoryAccessor extends Accessor{

    @Override
    public JSONArray httpsGet(String url) throws IOException {
        Accessor<HttpsURLConnection> accessor = new Accessor<>();
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
}
