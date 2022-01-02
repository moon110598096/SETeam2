package adapter.servlet;

import org.json.JSONArray;
import org.json.JSONObject;
import usecase.GithubRepositoryAccessor;

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
        JSONObject requestBody = new JSONObject(request.getReader().readLine());
        String component = requestBody.getString("component");
        JSONObject bugInfo = getBugInfoJsonArray(component);   //要回傳string還是JSONArray
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
        GithubRepositoryAccessor accessor = new GithubRepositoryAccessor();
        JSONArray jsonArray = accessor.httpsGet(apiUrl);
        JSONArray componentJsonArray = jsonArray.getJSONObject(0).getJSONArray("component");    //?
        JSONArray measuresJsonArray = componentJsonArray.getJSONObject(0).getJSONArray("measures");
        JSONObject bugResult = new JSONObject();
        for (Object bugObject : measuresJsonArray) {
            JSONObject bugJsonObject = (JSONObject) bugObject;

            bugResult = bugJsonObject.getJSONObject("value");
        }
        return bugResult;
    }
}
