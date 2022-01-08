package adapter.servlet.sonarqube;

import adapter.servlet.sonarqube.BugServlet;
import adapter.servlet.sonarqube.CodeSmellsServlet;
import adapter.servlet.sonarqube.VulnerabilityServlet;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SonarqubeProjectAnalysisServletTest  {
    private MockHttpServletRequest request;
    private HttpServletResponse response;
    private BugServlet bugServlet;
    private VulnerabilityServlet vulnerabilityServlet;
    private CodeSmellsServlet codeSmellsServlet;
//    private JSONObject obj;

    @Before
    public void setUp() throws IOException {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        bugServlet = new BugServlet();
        vulnerabilityServlet = new VulnerabilityServlet();
        codeSmellsServlet = new CodeSmellsServlet();
//        obj = new JSONObject();
//        obj.put("component","HappyCamp");
        request.addHeader("Content-Type","text/json");
        request.setContent("{component:GitRepositoryAnalysisSystem}".getBytes(StandardCharsets.UTF_8));
//        request.setContent("{component:HappyCamp}".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void GetBugTest() throws IOException {
        bugServlet.doPost(request, response);
        JSONObject jsonObject = (JSONObject) request.getAttribute("bug_info");
        System.out.println(jsonObject);
        Assert.assertEquals(true, jsonObject.getInt("value") >= 0);
    }

    @Test
    public void GetVulnerabilityTest() throws IOException {
        vulnerabilityServlet.doPost(request, response);
        JSONObject jsonObject = (JSONObject) request.getAttribute("vulnerability_info");
        Assert.assertEquals(true, jsonObject.getInt("value") >= 0);

    }

    @Test
    public void GetCodeSmellTest() throws IOException {
        codeSmellsServlet.doPost(request, response);
        JSONObject jsonObject = (JSONObject) request.getAttribute("code_smell_info");
        Assert.assertEquals(true, jsonObject.getInt("value") >= 0);
    }
}
