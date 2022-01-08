package adapter.servlet.sonarqube;

import org.json.JSONArray;
import org.json.JSONObject;
import usecase.SonarQubeRepositoryAccessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
public class HistoryServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject requestBody = new JSONObject(request.getReader().readLine());
        String component = requestBody.getString("component");

        JSONObject historyInfo = getHistoryJSONObject(component);

        request.setAttribute("history_info", historyInfo);

        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(historyInfo);
        System.out.println(historyInfo);
        out.flush();
        out.close();
    }

    private JSONObject getHistoryJSONObject(String component) throws IOException {
        String apiUrl = "http://140.124.181.10:9000/api/measures/search_history?component=" + component+"&metrics=bugs%2Ccode_smells%2Cvulnerabilities%2Creliability_rating%2Csecurity_rating%2Csqale_rating&ps=1000";
        SonarQubeRepositoryAccessor accessor = new SonarQubeRepositoryAccessor();
        JSONArray jsonArray = accessor.httpGet(apiUrl);
        JSONArray measuresJSONArray = jsonArray.getJSONObject(0).getJSONArray("measures");
        JSONObject measuresResultJSONObject = new JSONObject();
        measuresResultJSONObject.put("measures",measuresJSONArray);
        return measuresResultJSONObject;
    }
}
