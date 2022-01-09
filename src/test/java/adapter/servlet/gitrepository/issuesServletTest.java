package adapter.servlet.gitrepository;

import adapter.servlet.gitrepository.IssuesServlet;
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
import java.nio.charset.StandardCharsets;

public class issuesServletTest {
    private MockHttpServletRequest request;
    private HttpServletResponse response;
    private IssuesServlet issuesServlet;

    @Before
    public void setUp(){
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        issuesServlet = new IssuesServlet();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("owner", "octocat");
        jsonObject.put("repo", "hello-world");
        String content = "{repoInfo:" + jsonObject + "}";
        request.setContent(content.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void GetPersonalCommitsStatsTest() throws IOException {
        issuesServlet.doPost(request, response);
        JSONArray jsonArray = (JSONArray) request.getAttribute("issues_info");
        String state = jsonArray.getJSONObject(0).get("state").toString();

        Assert.assertEquals(true, jsonArray.length() > 0);
        Assert.assertEquals(true, state.equals("open") || state.equals("closed"));
        Assert.assertNotEquals(null, jsonArray.getJSONObject(0).get("title"));
    }
}
