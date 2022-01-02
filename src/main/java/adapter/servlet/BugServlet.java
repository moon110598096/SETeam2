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

public class BugServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject requestBody = (JSONObject) request.getAttribute("repoInfo");
        String component = requestBody.getString("component");
        JSONObject bugInfo = getBugInfoJsonArray(component);
        request.setAttribute("bug_info", bugInfo);

        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(bugInfo);
        System.out.println(bugInfo);
        out.flush();
        out.close();
    }

    private JSONObject getBugInfoJsonArray(String component) throws IOException {
        String apiUrl = "http://140.124.184.179:9000/api/measures/component?component=" + component + "&metricKeys=bugs";
        SonarQubeRepositoryAccessor accessor = new SonarQubeRepositoryAccessor();
        JSONArray jsonArray = accessor.httpGet(apiUrl);
        JSONObject componentJSONObject = jsonArray.getJSONObject(0).getJSONObject("component");
        JSONArray measuresJsonArray = componentJSONObject.getJSONArray("measures");
        JSONObject bugResult = new JSONObject();
        bugResult.put("value", measuresJsonArray.getJSONObject(0).getString("value"));
        return bugResult;
    }
}
