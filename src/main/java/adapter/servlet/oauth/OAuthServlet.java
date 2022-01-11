package adapter.servlet.oauth;

import adapter.account.AccountRepositoryImpl;
import adapter.account.CreateAccountInputImpl;
import adapter.account.CreateAccountOutputImpl;
import domain.Account;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import adapter.accessor.GithubOAuthAccessor;
import usecase.account.AccountRepository;
import usecase.account.CreateAccountInput;
import usecase.account.CreateAccountOutput;
import usecase.oauth.OAuthLoginUseCase;

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

    private JSONObject OAuthLogin(String code) throws IOException {
        System.out.println("OAuthLogin");
        System.out.println(code);

        JSONObject jsonObject = new JSONObject();

        String token = getToken(code);
        JSONObject user = getUser(token);

        AccountRepository accountRepository = new AccountRepositoryImpl();
        CreateAccountInput input = new CreateAccountInputImpl();
        input.setGithubId(user.getString("id"));
        input.setName(user.getString("login"));

        CreateAccountOutput output = new CreateAccountOutputImpl();
        OAuthLoginUseCase oAuthUseCase = new OAuthLoginUseCase(accountRepository);
        oAuthUseCase.execute(input, output);

        Account account = accountRepository.getAccountById(output.getId());

        jsonObject.append("valid", "true");
        jsonObject.append("name", account.getName());
        jsonObject.append("id", account.getId());
        jsonObject.append("redirect", "choose-project");

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

        JSONObject response;

        try {
            GithubOAuthAccessor accessor = new GithubOAuthAccessor();
            response = accessor.httpPost(url, body.toString());
            String token = response.get("access_token").toString();
            System.out.println(response);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject getUser(String token) throws IOException {
        System.out.println("getUser");

        String url = "https://api.github.com/user";

        JSONObject user = new JSONObject();

        GithubOAuthAccessor accessor = new GithubOAuthAccessor();
        accessor.addHTTPSGetProperty("Content-Type", "application/json");
        accessor.addHTTPSGetProperty("Authorization", "Bearer "+token);
        JSONArray jsonArray = accessor.httpsGet(url);
        user.put("id", jsonArray.getJSONObject(0).get("id").toString());
        user.put("login", jsonArray.getJSONObject(0).get("login").toString());

        return user;
    }
}
