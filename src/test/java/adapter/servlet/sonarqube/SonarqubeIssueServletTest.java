package adapter.servlet.sonarqube;

import adapter.servlet.sonarqube.IssueServlet;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SonarqubeIssueServletTest {
    private MockHttpServletRequest request;
    private HttpServletResponse response;
    private IssueServlet issueServlet;

    @Before
    public void setUp() throws IOException {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        issueServlet = new IssueServlet();
        request.addHeader("Content-Type","text/json");
        request.setContent("{componentKeys:GitRepositoryAnalysisSystem}".getBytes(StandardCharsets.UTF_8));

    }
    @Test
    public void GetIssueMessageTest() throws IOException {
        issueServlet.doPost(request, response);
        JSONObject jsonObject = (JSONObject) request.getAttribute("issue_message_info");
        System.out.println(jsonObject);
        Assert.assertNotNull(jsonObject.getJSONArray("bugs"));
        Assert.assertNotNull(jsonObject.getJSONArray("code_smells"));
        Assert.assertNotNull(jsonObject.getJSONArray("vulnerabilities"));
    }
}
