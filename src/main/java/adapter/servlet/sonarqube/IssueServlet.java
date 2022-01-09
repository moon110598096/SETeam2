package adapter.servlet.sonarqube;

import org.json.JSONArray;
import org.json.JSONObject;
import usecase.SonarQubeRepositoryAccessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class IssueServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject requestBody = new JSONObject(request.getReader().readLine());
        String componentKeys = requestBody.getString("componentKeys");

        JSONObject issue_messageInfo = getIssueMessageJSONObject(componentKeys);

        request.setAttribute("issue_message_info", issue_messageInfo);

        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(issue_messageInfo);
        System.out.println(issue_messageInfo);
        out.flush();
        out.close();
    }

    private JSONObject getIssueMessageJSONObject(String componentKeys) throws IOException {
        String apiUrl = "http://140.124.181.10:9000/api/issues/search?componentKeys=" + componentKeys + "&ps=500&types=CODE_SMELL,BUG,VULNERABILITY&statuses=OPEN";
        SonarQubeRepositoryAccessor accessor = new SonarQubeRepositoryAccessor();
        JSONArray jsonArray = accessor.httpGet(apiUrl);
        JSONArray issueJSONArray = jsonArray.getJSONObject(0).getJSONArray("issues");

        JSONObject issue_messageResultJSONObject = new JSONObject();
        JSONArray bugJSONArray = new JSONArray();
        JSONArray code_smellJSONArray = new JSONArray();
        JSONArray vulnerabilityJSONArray = new JSONArray();
        int count = 0;
        int countBug =0;
        int countB =0;
        int countV = 0;
        for(Object statsObject : issueJSONArray){
            JSONObject statsJsonObject = (JSONObject) statsObject;
            JSONObject tempJSONObject = new JSONObject();
            count++;
            if(statsJsonObject.getString("type").equals("BUG")){
                tempJSONObject.put("message", statsJsonObject.getString("message"));
                bugJSONArray.put(tempJSONObject);
                countBug ++;
            }else if(statsJsonObject.getString("type").equals("CODE_SMELL")){
                tempJSONObject.put("message", statsJsonObject.getString("message"));
                code_smellJSONArray.put(tempJSONObject);
                countB ++;
            }else if(statsJsonObject.getString("type").equals("VULNERABILITY")){
                tempJSONObject.put("message", statsJsonObject.getString("message"));
                vulnerabilityJSONArray.put(tempJSONObject);
                countV ++;
            }
        }
        System.out.println(count);
        System.out.println(countBug);
        System.out.println(countB);
        System.out.println(countV);
        issue_messageResultJSONObject.put("bugs",bugJSONArray);
        issue_messageResultJSONObject.put("code_smells",code_smellJSONArray);
        issue_messageResultJSONObject.put("vulnerabilities",vulnerabilityJSONArray);

        return issue_messageResultJSONObject;
    }

}
