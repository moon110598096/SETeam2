package adapter.servlet.project;

import adapter.servlet.project.LoginServlet;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoginServletTest {
    private MockHttpServletRequest request;
    private HttpServletResponse response;
    private LoginServlet loginServlet;

    @Before
    public void setUp(){
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        loginServlet = new LoginServlet();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("account", "tt");
        jsonObject.put("password", "tt");
        request.setContent(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void LoginServletSuccessTest() throws ServletException, IOException {
        loginServlet.doPost(request, response);

        JSONObject jsonObject = (JSONObject)request.getAttribute("result");

        Assert.assertEquals("true", jsonObject.getJSONArray("valid").get(0));
    }

    @Test
    public void LoginServletFailTest() throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("account", "ShouldFail");
        jsonObject.put("password", "ShouldFail");
        request.setContent(jsonObject.toString().getBytes(StandardCharsets.UTF_8));

        loginServlet.doPost(request, response);

        jsonObject = (JSONObject)request.getAttribute("result");

        Assert.assertEquals("false", jsonObject.getJSONArray("valid").get(0));
    }
}
