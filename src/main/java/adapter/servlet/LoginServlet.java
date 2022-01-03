package adapter.servlet;
import adapter.account.AccountRepositoryImpl;
import domain.Account;
import org.json.JSONObject;
import usecase.account.AccountRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/userLogin", name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //    https://github.com/login/oauth/authorize?client_id=2ba7827da6b98900790e 前端加一個按鈕 指向此網址
        //  User 登入完後 會回到此頁面
        // http://localhost:8080/GitRepositoryAnalysisSystem/frontEnd/userLogin?code=714adb7907ec5ce330c4
        //        String code = request.getParameter("code");
        //        PrintWriter out = response.getWriter();
        //        out.println(code);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // dependency 超亂(bad smell but no time.QQ)
        JSONObject jsonObject = new JSONObject();
        AccountRepository accountRepository = new AccountRepositoryImpl();
        JSONObject requestBody = new JSONObject(request.getReader().readLine());
        Account account = new Account(
                requestBody.getString("account"),
                requestBody.getString("password")
        );
        boolean isAccountValid = accountRepository.verifyAccount(account);
        account = accountRepository.getAccountByAccountAndPassword(account);
        if (isAccountValid){
            jsonObject.append("valid", "true");
            jsonObject.append("userName", account.getName());
            jsonObject.append("userId", account.getId());
            jsonObject.append("redirect", "choose-project");
        }
        else{
            jsonObject.append("valid", "false");
        }

        request.setAttribute("result", jsonObject);
        response.setContentType("text/json");
        PrintWriter out = response.getWriter();
        out.println(jsonObject) ;
        out.close();
    }
}