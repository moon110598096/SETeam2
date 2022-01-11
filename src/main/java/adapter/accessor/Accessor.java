package adapter.accessor;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Accessor <T extends HttpURLConnection>{
    protected InputStream is;
    protected InputStreamReader isr;

    protected HttpURLConnection connection;

    protected Map<String, String> properties;

    public Accessor(){
        properties = new HashMap<>();
    }

    public void addHTTPSGetProperty(String property, String value){
        this.properties.put(property, value);
    }

    protected T getConnection(String url) throws IOException {
        URL requestUrl = new URL(url);
        return (T)requestUrl.openConnection();
    }

    protected void setConnectionProperty(T conn){
        for(String property : this.properties.keySet())
            conn.setRequestProperty(property, this.properties.get(property));
    }

    protected BufferedReader getJSONUsingHttpsGet(T httpsConnection) throws IOException {
        is = httpsConnection.getInputStream();
        isr = new InputStreamReader(is, "UTF-8");
        return new BufferedReader(isr);
    }

    protected String getCompleteContentString(BufferedReader reader) throws IOException {
        StringBuilder compeleteContent = new StringBuilder(0);
        String line = "";
        while ((line = reader.readLine()) != null) compeleteContent.append(line);
        return compeleteContent.toString();
    }

    protected void closeAllConnection() throws IOException {
        is.close();
        isr.close();
    }

    protected void setOutputStream(String paramsJSON) throws IOException {
        try (OutputStream os = connection.getOutputStream()) {
                byte[] input = paramsJSON.getBytes("UTF-8");
                os.write(input, 0, input.length);
        }
    }
}
