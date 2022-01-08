package adapter.servlet.sonarqube;

import adapter.servlet.sonarqube.HistoryServlet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SonarqubeHistoryServletTest {
    private MockHttpServletRequest request;
    private HttpServletResponse response;
    private HistoryServlet historyServlet;

    @Before
    public void setUp() throws IOException {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        historyServlet = new HistoryServlet();
        request.addHeader("Content-Type","text/json");
        request.setContent("{component:GitRepositoryAnalysisSystem}".getBytes(StandardCharsets.UTF_8));

    }

    @Test
    public void GetHistoryTest() throws IOException {
        historyServlet.doPost(request, response);
        JSONObject JsonObject = (JSONObject) request.getAttribute("history_info");
        Assert.assertNotNull(JsonObject);
    }
}
