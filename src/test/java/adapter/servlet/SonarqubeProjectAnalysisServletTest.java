package adapter.servlet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SonarqubeProjectAnalysisServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private BugServlet bugServlet;
    private VulnerabilityServlet vulnerabilityServlet;
    private CodeSmellsServlet codeSmellsServlet;

    @Before
    public void setUp(){
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        bugServlet = new BugServlet();
        vulnerabilityServlet = new VulnerabilityServlet();
        codeSmellsServlet = new CodeSmellsServlet();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("component", "HappyCamp");
        request.setAttribute("repoInfo", jsonObject);
    }

    @Test
    public void GetBugTest() throws IOException {
        bugServlet.doPost(request, response);
        JSONObject jsonObject = (JSONObject) request.getAttribute("bug_info");
        System.out.println(jsonObject);
        Assert.assertEquals(1, jsonObject.getInt("value"));
    }

    @Test
    public void GetVulnerabilityTest() throws IOException {
        vulnerabilityServlet.doPost(request, response);
        JSONObject jsonObject = (JSONObject) request.getAttribute("vulnerability_info");
        Assert.assertEquals(2, jsonObject.getInt("value"));
    }

    @Test
    public void GetCodeSmellTest() throws IOException {
        codeSmellsServlet.doPost(request, response);
        JSONObject jsonObject = (JSONObject) request.getAttribute("code_smell_info");
        Assert.assertEquals(4, jsonObject.getInt("value"));
    }
}
