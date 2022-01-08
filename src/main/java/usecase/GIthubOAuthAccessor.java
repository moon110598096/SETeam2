package usecase;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class GIthubOAuthAccessor {
    public JSONObject httpGet(String url, String paramsJSON, String token){
        HttpsURLConnection con = null;
        URL urlObj;
        StringBuilder result = null;
        JSONObject jObj = null;

        try {
            urlObj = new URL(url);
            con = (HttpsURLConnection) urlObj.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer "+token);

            int code = con.getResponseCode();
            System.out.println("HTTP CODE " + String.valueOf(code));;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //Receive the response from the server
            InputStream in = new BufferedInputStream(con.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            System.out.println("JSON Parser "+ "result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        con.disconnect();

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(result.toString());
            System.out.println(jObj.toString());
        } catch (JSONException e) {
            System.out.println("JSON Parser " + "Error parsing data " + e.toString());
        }

        return jObj;
    }

    public JSONObject httpPost(String url, String paramsJSON){
        HttpsURLConnection con = null;
        URL urlObj;
        StringBuilder result = null;
        JSONObject jObj = null;

        try {
            urlObj = new URL(url);
            con = (HttpsURLConnection) urlObj.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Access-Control-Allow-Origin", "github.com.*");
            con.setDoOutput(true);
            con.setReadTimeout(60000);
            con.setConnectTimeout(60000);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = paramsJSON.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            int code = con.getResponseCode();
            System.out.println("HTTP CODE " + String.valueOf(code));;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //Receive the response from the server
            InputStream in = new BufferedInputStream(con.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            System.out.println("JSON Parser "+ "result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        con.disconnect();

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(result.toString());
            System.out.println(jObj.toString());
        } catch (JSONException e) {
            System.out.println("JSON Parser " + "Error parsing data " + e.toString());
        }

        return jObj;
    }
}
