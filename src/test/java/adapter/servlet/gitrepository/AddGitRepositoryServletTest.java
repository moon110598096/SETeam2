package adapter.servlet.gitrepository;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AddGitRepositoryServletTest {
    private MockHttpServletRequest request;
    private HttpServletResponse response;
    private AddGitRepositoryServlet addGitRepositoryServlet;

    @Before
    public void setUp(){
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        addGitRepositoryServlet = new AddGitRepositoryServlet();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("projectId", "testProjectId");
        jsonObject.put("githubUrl", "testGithubUrl");
        request.setContent(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void AddGitRepositoryServletFailTest() throws IOException {
        try {
            addGitRepositoryServlet.doPost(request, response);

            Assert.assertEquals("false", request.getAttribute("isSuccess"));
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void AddGitRepositoryServletSuccessTest() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("projectId", "4061b41e-fd42-4434-92fb-5a63047ba22d");
        jsonObject.put("githubUrl", "testGithubUrl");
        request.setContent(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
        try {
            addGitRepositoryServlet.doPost(request, response);

            Assert.assertEquals("true", request.getAttribute("isSuccess"));
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

}
