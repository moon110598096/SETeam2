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
//    private HttpServletRequest request;
//    private HttpServletResponse response;
//    private BugServlet bugServlet;
//    private VulnerabilityServlet vulnerabilityServlet;
//    private CodeSmellsServlet codeSmellsServlet;
//
//    @Before
//    public void setUp(){
//        request = new MockHttpServletRequest();
//        response = new MockHttpServletResponse();
//        bugServlet = new BugServlet();
//        vulnerabilityServlet = new VulnerabilityServlet();
//        codeSmellsServlet = new CodeSmellsServlet();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("component", "HappyCamp");
//        request.setAttribute("repoInfo", jsonObject);  //需更改
//    }
//
//    @Test
//    public void GetBugTest() throws IOException {
//        BugServlet.doPost(request, response);  //改doGet?
//        JSONArray jsonArray = (JSONArray) request.getAttribute("measures");
//        Assert.assertEquals("bugs", jsonArray.getJSONObject(0).getString("metric"));
//        Assert.assertEquals(1, jsonArray.getJSONObject(0).getInt("value"));
//    }
//
//    @Test
//    public void GetVulnerabilityTest() throws IOException {
//        vulnerabilityServlet.doPost(request, response);  //改doGet?
//        JSONArray jsonArray = (JSONArray) request.getAttribute("measures");
//        Assert.assertEquals("vulnerabilities", jsonArray.getJSONObject(0).getString("metric"));
//        Assert.assertEquals(2, jsonArray.getJSONObject(0).getInt("value"));
//    }

//    @Test
//    public void GetCodeSmellTest() throws IOException {
//        codeSmellsServlet.doPost(request, response);  //改doGet?
//        JSONArray jsonArray = (JSONArray) request.getAttribute("measures");
//        Assert.assertEquals("code_smells", jsonArray.getJSONObject(0).getString("metric"));
//        Assert.assertEquals(4, jsonArray.getJSONObject(0).getInt("value"));
//    }

}
