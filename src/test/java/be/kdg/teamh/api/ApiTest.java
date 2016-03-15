package be.kdg.teamh.api;

import be.kdg.teamh.Application;
import be.kdg.teamh.dtos.request.LoginRequest;
import be.kdg.teamh.dtos.response.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class ApiTest
{
    protected MockMvc http;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected ObjectMapper objectMapper;

    @Before
    public void setUp()
    {
        this.http = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    protected MockHttpServletRequestBuilder get(String url) throws Exception
    {
        return MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder post(String url, String json) throws Exception
    {
        return MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(json);
    }

    protected MockHttpServletRequestBuilder put(String url, String json) throws Exception
    {
        return MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON).content(json);
    }

    protected MockHttpServletRequestBuilder delete(String url) throws Exception
    {
        return MockMvcRequestBuilders.delete(url).contentType(MediaType.APPLICATION_JSON);
    }

    protected String getUserToken() throws Exception
    {
        MvcResult result = http.perform(post("/auth/login", objectMapper.writeValueAsString(new LoginRequest("user", "user")))).andReturn();

        return "Bearer " + objectMapper.readValue(result.getResponse().getContentAsString(), LoginResponse.class).getToken();
    }

    protected String getAdminToken() throws Exception
    {
        MvcResult result = http.perform(post("/auth/login", objectMapper.writeValueAsString(new LoginRequest("admin", "admin")))).andReturn();

        return "Bearer " + objectMapper.readValue(result.getResponse().getContentAsString(), LoginResponse.class).getToken();
    }

    protected String getNonExistingUserToken() throws Exception
    {
        MvcResult result = http.perform(post("/auth/login", objectMapper.writeValueAsString(new LoginRequest("wrong", "wrong")))).andReturn();

        return "Bearer " + objectMapper.readValue(result.getResponse().getContentAsString(), LoginResponse.class).getToken();
    }
}
