package adapter.servlet;

import org.json.JSONArray;
import org.json.JSONObject;
import usecase.GithubRepositoryAccessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CodeSmellsServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject requestBody = new JSONObject(request.getReader().readLine());
        String component = requestBody.getString("component");
        JSONObject code_smellInfo = getBugInfoJsonArray(component);   //要回傳string還是JSONArray
        request.setAttribute("code_smell_info", code_smellInfo);

        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(code_smellInfo);
        System.out.println(code_smellInfo);
        out.flush();
        out.close();
    }

    private JSONObject getBugInfoJsonArray(String component) throws IOException {
        String apiUrl = "http://140.124.184.179:9000/api/measures/component?component=" + component + "&metricKeys=code_smells";
        GithubRepositoryAccessor accessor = new GithubRepositoryAccessor();
        JSONArray jsonArray = accessor.httpsGet(apiUrl);
        JSONArray componentJsonArray = jsonArray.getJSONObject(0).getJSONArray("component");    //?
        JSONArray measuresJsonArray = componentJsonArray.getJSONObject(0).getJSONArray("measures");
        JSONObject code_smellResult = new JSONObject();
        for (Object code_smellObject : measuresJsonArray) {
            JSONObject code_smellJsonObject = (JSONObject) code_smellObject;

            code_smellResult = code_smellJsonObject.getJSONObject("value");
        }
        return code_smellResult;
    }
}
