package adapter.servlet;

import adapter.account.AccountRepositoryImpl;
import adapter.account.CreateAccountInputImpl;
import adapter.account.CreateAccountOutputImpl;
import domain.Account;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import usecase.account.AccountRepository;
import usecase.account.CreateAccountInput;
import usecase.account.CreateAccountOutput;
import usecase.account.CreateAccountUseCase;

@WebServlet(urlPatterns = "/OAuthorize", name = "OAuthServlet")
public class OAuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost");
        JSONObject requestBody = new JSONObject(request.getReader().readLine());

        String code =  requestBody.getString("code");
        JSONObject jsonObject = OAuthLogin(code);

        request.setAttribute("result", jsonObject);
        response.setContentType("text/json");
        PrintWriter out = response.getWriter();
        out.println(jsonObject) ;
        out.close();
    }

    private JSONObject OAuthLogin(String code){
        System.out.println("OAuthLogin");
        System.out.println(code);

        JSONObject jsonObject = new JSONObject();

        String token = getToken(code);
        JSONObject user = getUser(token);

        Account fakeAccount = new Account();
        AccountRepository accountRepository = new AccountRepositoryImpl();

        fakeAccount.setGithubId(user.getString("id"));

        boolean githubAccountExist = accountRepository.verifyGithubAccount(fakeAccount);

        if (githubAccountExist){
            Account account = accountRepository.getAccountByGithubId(fakeAccount);

            jsonObject.append("valid", "true");
            jsonObject.append("name", account.getName());
            jsonObject.append("id", account.getId());
            jsonObject.append("redirect", "choose-project");
        }
        else {
            CreateAccountInput input = new CreateAccountInputImpl();
            input.setGithubId(user.getString("id"));
            input.setName(user.getString("login"));

            CreateAccountOutput output = new CreateAccountOutputImpl();
            CreateAccountUseCase createAccountUseCase = new CreateAccountUseCase(accountRepository);
            createAccountUseCase.executeOAuth(input, output);
            jsonObject.append("valid", "true");
            jsonObject.append("userName", fakeAccount.getName());
            jsonObject.append("userId", fakeAccount.getId());
            jsonObject.append("redirect", "choose-project");
        }

        return jsonObject;
    }

    private String getToken(String code) {
        System.out.println("getToken");

        String url = "https://github.com/login/oauth/access_token";
        String clientId = "2ba7827da6b98900790e";
        String clientSecrets = "701cdc662f8d414730a49ee1c0abf5f3ee82aa3c";

        JSONObject body = new JSONObject();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecrets);
        body.put("code", code);

        JSONObject response = new JSONObject();

        try {
            response = makeHttpRequest(url, body.toString(), null);
            String token = response.get("access_token").toString();
            System.out.println(response.toString());
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject getUser(String token) {
        System.out.println("getUser");

        String url = "https://api.github.com/user";
        JSONObject body = new JSONObject();
//        body.put("code", code);

        JSONObject response;

        JSONObject user = new JSONObject();
        try {
            response = makeHttpRequest(url, body.toString(), token);
            user.put("id", response.get("id").toString());
            user.put("login", response.get("login").toString());

            System.out.println(response.toString());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject makeHttpRequest(String url, String paramsJSON, String token){
        HttpsURLConnection con = null;
        URL urlObj;
        StringBuilder result = null;
        JSONObject jObj = null;

        try {
            urlObj = new URL(url);
            con = (HttpsURLConnection) urlObj.openConnection();

            con.setRequestProperty("Content-Type", "application/json");

            if (token != null){
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", "Bearer "+token);
            }else{
                con.setRequestMethod("POST");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Access-Control-Allow-Origin", "github.com.*");
                con.setDoOutput(true);
                con.setReadTimeout(60000);
                con.setConnectTimeout(60000);
            }

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
