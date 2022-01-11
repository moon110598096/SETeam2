package adapter.accessor;

import org.json.JSONArray;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;

public class GithubRepositoryAccessor extends Accessor{

    public JSONArray httpsGet(String url) throws IOException {
        Accessor<HttpsURLConnection> accessor = new Accessor<>();
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
}
