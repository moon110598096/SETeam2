package adapter.servlet;

import org.json.JSONArray;
import org.json.JSONObject;
import usecase.SonarQubeRepositoryAccessor;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/CodeSmell", name = "CodeSmellServlet")
public class CodeSmellsServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject requestBody = new JSONObject(request.getReader().readLine());
        String component = requestBody.getString("component");
        System.out.println(component);
        JSONObject code_smellInfo = getCodeSmellInfoJsonArray(component);
        request.setAttribute("code_smell_info", code_smellInfo);

        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(code_smellInfo);
        System.out.println(code_smellInfo);
        System.out.println("test");
        out.flush();
        out.close();
    }

    private JSONObject getCodeSmellInfoJsonArray(String component) throws IOException {
        String apiUrl = "http://140.124.184.179:9000/api/measures/component?component=" + component + "&metricKeys=code_smells";
        SonarQubeRepositoryAccessor accessor = new SonarQubeRepositoryAccessor();
        JSONArray jsonArray = accessor.httpGet(apiUrl);
        JSONObject componentJSONObject = jsonArray.getJSONObject(0).getJSONObject("component");
        JSONArray measuresJsonArray = componentJSONObject.getJSONArray("measures");
        JSONObject code_smellResult = new JSONObject();
        code_smellResult.put("value", measuresJsonArray.getJSONObject(0).getString("value"));
        return code_smellResult;
    }
}
